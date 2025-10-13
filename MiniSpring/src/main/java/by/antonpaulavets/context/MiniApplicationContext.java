package by.antonpaulavets.context;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import by.antonpaulavets.annotations.Autowired;
import by.antonpaulavets.annotations.Component;
import by.antonpaulavets.annotations.Scope;

public class MiniApplicationContext {

    private final String basePackage;
    private final Map<Class<?>, Object> beanInstances = new HashMap<Class<?>,Object>();

    public MiniApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        try {
            refresh();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize application context", e);
        }
    }

    private void refresh() throws Exception {
        scanAndInstantiateBeans();
        injectDependencies();
        initializeBeans();
    }

    private void scanAndInstantiateBeans() throws Exception {
        Reflections reflections = new Reflections(basePackage, new TypeAnnotationsScanner());
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> componentClass : componentClasses) {
            Scope scopeAnnotation = componentClass.getAnnotation(Scope.class);
            String scopeValue = (scopeAnnotation != null) ? scopeAnnotation.value() : "singleton";


            if (scopeValue.equals("singleton")) {
                Object instance = componentClass.getDeclaredConstructor().newInstance();
                beanInstances.put(componentClass, instance);
            }


        }
    }


    private void injectDependencies() throws Exception {
        for (Object beanInstance : beanInstances.values()) {

            Class<?> beanClass = beanInstance.getClass();
            Field[] declaredFields = beanClass.getDeclaredFields();

            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class<?> fieldType = field.getType();

                    Object dependency = getBean(fieldType);
                    if (dependency == null) {
                        throw new IllegalStateException("No bean of type " + fieldType.getName() + " found for injection into " + beanClass.getName() + "." + field.getName());
                    }

                    field.setAccessible(true);
                    field.set(beanInstance, dependency);
                }
            }
        }
    }


    private void initializeBeans() throws Exception {
        for (Object beanInstance : beanInstances.values()) {
            if (beanInstance instanceof InitializingBean) {
                ((InitializingBean) beanInstance).afterPropertiesSet();
            }
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        if (beanInstances.containsKey(type)) {
            return (T) beanInstances.get(type);

        } else {

            for (Map.Entry<Class<?>, Object> entry : beanInstances.entrySet()) {
                if (type.isAssignableFrom(entry.getKey())) {
                    Scope scopeAnnotation = entry.getKey().getAnnotation(Scope.class);
                    String scopeValue = (scopeAnnotation != null) ? scopeAnnotation.value() : "singleton";

                    if (scopeValue.equals("prototype")) {
                        try {
                            Object instance = entry.getKey().getDeclaredConstructor().newInstance();
                            Class<?> beanClass = instance.getClass();
                            Field[] declaredFields = beanClass.getDeclaredFields();

                            for (Field field : declaredFields) {
                                if (field.isAnnotationPresent(Autowired.class)) {
                                    Class<?> fieldType = field.getType();
                                    Object dependency = getBean(fieldType);
                                    if (dependency == null) {
                                        throw new IllegalStateException("No bean of type " + fieldType.getName() + " found for injection into " + beanClass.getName() + "." + field.getName());
                                    }
                                    field.setAccessible(true);
                                    field.set(instance, dependency);
                                }
                            }
                            if (instance instanceof InitializingBean) {
                                ((InitializingBean) instance).afterPropertiesSet();
                            }
                            return (T) instance;

                        } catch (Exception e) {
                            throw new RuntimeException("Failed to create prototype bean: " + entry.getKey().getName(), e);
                        }
                    }
                }
            }
        }

        return null;
    }
}
