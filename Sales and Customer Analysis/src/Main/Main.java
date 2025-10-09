package Main;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Customer customer1 = new Customer("1", "Anton Paulavets", "Anton.Paulavets@gmail.com", LocalDateTime.now().minusYears(2), 30, "Minsk");
        Customer customer2 = new Customer("2", "Dana Serses", "Dana.Serses@gmail.com", LocalDateTime.now().minusYears(1), 25, "Gomel");
        Customer customer3 = new Customer("3", "Pasha Golubev", "Pasha.Golubev@gmail.com", LocalDateTime.now().minusYears(3), 40, "Grodno");

        OrderItem item1 = new OrderItem("Laptop", 1, 1200.0, Category.ELECTRONICS);
        OrderItem item2 = new OrderItem("T-shirt", 2, 25.0, Category.CLOTHING);
        OrderItem item3 = new OrderItem("Book", 1, 20.0, Category.BOOKS);
        OrderItem item4 = new OrderItem("Headphones", 1, 100.0, Category.ELECTRONICS);
        OrderItem item5 = new OrderItem("Shoes", 1, 80.0, Category.CLOTHING);
        OrderItem item6 = new OrderItem("Laptop", 1, 1200.0, Category.ELECTRONICS);

        Order order1 = new Order("101", LocalDateTime.now().minusDays(2), customer1, Arrays.asList(item1, item2), OrderStatus.DELIVERED);
        Order order2 = new Order("102", LocalDateTime.now().minusDays(1), customer2, List.of(item3), OrderStatus.SHIPPED);
        Order order3 = new Order("103", LocalDateTime.now(), customer1, Arrays.asList(item4, item5), OrderStatus.DELIVERED);
        Order order4 = new Order("104", LocalDateTime.now(), customer1, Arrays.asList(item6), OrderStatus.DELIVERED);
        Order order5 = new Order("105", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED);
        Order order6 = new Order("106", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.CANCELLED);
        Order order7 = new Order("107", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED);
        Order order8 = new Order("108", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED);
        Order order9 = new Order("109", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED);
        Order order10 = new Order("110", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED);
        Order order11 = new Order("111", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED);

        List<Order> orders;
        orders = Arrays.asList(order1, order2, order3, order4,order5, order6, order7, order8, order9, order10, order11);
        AnalysisMetrics analysisMetrics = new AnalysisMetrics();
        System.out.println(analysisMetrics.getUniqueCities(orders));

    }
}