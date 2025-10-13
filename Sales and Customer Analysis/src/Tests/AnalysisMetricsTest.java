package Tests;

import Main.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnalysisMetricsTest {
    @Test
    void getUniqueCities_shouldReturnCorrectSetOfCities() {
        Customer customer1 = new Customer("1", "Anton Paulavets", "Anton.Paulavets@gmail.com", LocalDateTime.now().minusYears(2), 30, "Minsk");
        Customer customer2 = new Customer("2", "Dana Serses", "Dana.Serses@gmail.com", LocalDateTime.now().minusYears(1), 25, "Gomel");
        Customer customer3 = new Customer("3", "Pasha Golubev", "Pasha.Golubev@gmail.com", LocalDateTime.now().minusYears(3), 40, "Grodno");

        OrderItem item1 = new OrderItem("Laptop", 1, 1200.0, Category.ELECTRONICS);
        OrderItem item2 = new OrderItem("T-shirt", 2, 25.0, Category.CLOTHING);
        OrderItem item3 = new OrderItem("Book", 1, 20.0, Category.BOOKS);
        OrderItem item4 = new OrderItem("Headphones", 1, 100.0, Category.ELECTRONICS);
        OrderItem item5 = new OrderItem("Shoes", 1, 80.0, Category.CLOTHING);
        OrderItem item6 = new OrderItem("Laptop", 1, 1200.0, Category.ELECTRONICS);

        List<Order> orders = Arrays.asList(
                new Order("101", LocalDateTime.now().minusDays(2), customer1, Arrays.asList(item1, item2), OrderStatus.DELIVERED),
                new Order("102", LocalDateTime.now().minusDays(1), customer2, List.of(item3), OrderStatus.SHIPPED),
                new Order("103", LocalDateTime.now(), customer1, Arrays.asList(item4, item5), OrderStatus.DELIVERED),
                new Order("104", LocalDateTime.now(), customer1, Arrays.asList(item6), OrderStatus.DELIVERED),
                new Order("105", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED),
                new Order("106", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.CANCELLED),
                new Order("107", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED),
                new Order("108", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED),
                new Order("109", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED),
                new Order("110", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED),
                new Order("111", LocalDateTime.now(), customer2, List.of(item3), OrderStatus.DELIVERED)
        );
        AnalysisMetrics analysis = new AnalysisMetrics();
        Set<String> uniqueCities = analysis.getUniqueCities(orders);
        assertEquals(2, uniqueCities.size());
        assertTrue(uniqueCities.contains("Gomel"));
        assertTrue(uniqueCities.contains("Minsk"));
    }

    @Test
    void getTotalIncomeForCompletedOrders_shouldReturnCorrectTotalIncome() {
        AnalysisMetrics orderAnalysis = new AnalysisMetrics();
        OrderItem item1 = new OrderItem("Product A", 2, 10.0, Category.BOOKS);
        OrderItem item2 = new OrderItem("Product B", 1, 20.0, Category.ELECTRONICS);
        Customer customer1 = new Customer("1", "Anton Paulavets", "Anton.Paulavets@gmail.com", LocalDateTime.now().minusYears(2), 30, "Minsk");

        List<Order> orders = Arrays.asList(
                new Order("1", LocalDateTime.now(), customer1, List.of(item1, item2), OrderStatus.DELIVERED),
                new Order("2", LocalDateTime.now(), customer1, List.of(item1), OrderStatus.CANCELLED) // Should not be included
        );

        double totalIncome = orderAnalysis.getTotalIncomeForCompletedOrders(orders);
        assertEquals(40.0, totalIncome);
    }

    @Test
    void getMostPopularProductBySales_shouldReturnCorrectProduct() {
        AnalysisMetrics orderAnalysis = new AnalysisMetrics();
        OrderItem item1 = new OrderItem("Product A", 2, 10.0, Category.BOOKS);
        OrderItem item2 = new OrderItem("Product B", 1, 20.0, Category.ELECTRONICS);
        OrderItem item3 = new OrderItem("Product A", 3, 10.0, Category.BOOKS); // More sales for Product A
        Customer customer1 = new Customer("1", "Anton Paulavets", "Anton.Paulavets@gmail.com", LocalDateTime.now().minusYears(2), 30, "Minsk");

        List<Order> orders = Arrays.asList(
                new Order("1", LocalDateTime.now(), customer1, List.of(item1, item2), OrderStatus.DELIVERED),
                new Order("2", LocalDateTime.now(), customer1, List.of(item3), OrderStatus.DELIVERED)
        );

        String mostPopularProduct = orderAnalysis.getMostPopularProductBySales(orders);
        assertEquals("Product A", mostPopularProduct);
    }

    @Test
    void getAverageCheckForDeliveredOrders_shouldReturnCorrectAverage() {
        AnalysisMetrics orderAnalysis = new AnalysisMetrics();
        OrderItem item1 = new OrderItem("Product A", 2, 10.0, Category.BOOKS);
        OrderItem item2 = new OrderItem("Product B", 1, 20.0, Category.ELECTRONICS);
        OrderItem item3 = new OrderItem("Product C", 1, 5.0, Category.HOME);
        Customer customer1 = new Customer("1", "Anton Paulavets", "Anton.Paulavets@gmail.com", LocalDateTime.now().minusYears(2), 30, "Minsk");

        List<Order> orders = Arrays.asList(
                new Order("1", LocalDateTime.now(), customer1, List.of(item1, item2), OrderStatus.DELIVERED), // 40.0
                new Order("2", LocalDateTime.now(), customer1, List.of(item3), OrderStatus.DELIVERED),    // 5.0
                new Order("3", LocalDateTime.now(), customer1, List.of(item1), OrderStatus.CANCELLED)  // Should not be included
        );

        double averageCheck = orderAnalysis.getAverageCheckForDeliveredOrders(orders);
        assertEquals(22.5, averageCheck, 0.001); // Allow a small delta for double comparison.
    }

    @Test
    void getCustomersWithMoreThan5Orders_shouldReturnCorrectListOfCustomers() {
        AnalysisMetrics orderAnalysis = new AnalysisMetrics();
        Customer customer1 = new Customer("1", "Anton Paulavets", "Anton.Paulavets@gmail.com", LocalDateTime.now().minusYears(2), 30, "Minsk");
        Customer customer2 = new Customer("2", "Dana Serses", "Dana.Serses@gmail.com", LocalDateTime.now().minusYears(1), 25, "Gomel");

        List<Order> orders = new ArrayList<>();
        // Customer 1 has 6 orders
        for (int i = 0; i < 6; i++) {
            orders.add(new Order(String.valueOf(i), LocalDateTime.now(), customer1, List.of(), OrderStatus.DELIVERED));
        }

        // Customer 2 has 3 orders
        for (int i = 0; i < 3; i++) {
            orders.add(new Order(String.valueOf(i), LocalDateTime.now(), customer2, List.of(), OrderStatus.DELIVERED));
        }

        List<Customer> customersWithMoreThan5Orders = orderAnalysis.getCustomersWithMoreThan5Orders(orders);
        assertEquals(1, customersWithMoreThan5Orders.size());
        assertEquals(customer1, customersWithMoreThan5Orders.get(0));
    }
}