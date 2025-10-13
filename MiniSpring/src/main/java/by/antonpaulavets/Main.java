package by.antonpaulavets;


import by.antonpaulavets.annotations.Autowired;
import by.antonpaulavets.annotations.Component;
import by.antonpaulavets.annotations.Scope;
import by.antonpaulavets.context.InitializingBean;

@Component
@Scope("prototype")
class MyService {
    public String doSomething() {
        return "Service action";
    }
}

@Component
class MyComponent implements InitializingBean {

    @Autowired
    private MyService myService;

    private String message;

    public String getMessage() {
        return message + " " + myService.doSomething();
    }


    public void afterPropertiesSet() throws Exception {
        this.message = "Hello from MiniSpring";
    }
}


public class Main {
    public static void main(String[] args) {
        by.antonpaulavets.context.MiniApplicationContext context = new by.antonpaulavets.context.MiniApplicationContext("minispring");
        MyComponent myComponent = context.getBean(MyComponent.class);
        System.out.println(myComponent.getMessage());
    }
}