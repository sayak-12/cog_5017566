import java.util.*;
class Order {
    private String orderId;
    private String customerName;
    private double totalPrice;

    // Constructor
    public Order(String orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", customerName=" + customerName + ", totalPrice=" + totalPrice + "]";
    }
}
class BubbleSort {
    public static void bubbleSortByTotalPrice(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    // Swap orders[j] and orders[j + 1]
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }
}
class QuickSort {
    public static void quickSortByTotalPrice(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);
            quickSortByTotalPrice(orders, low, pi - 1);
            quickSortByTotalPrice(orders, pi + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                // Swap orders[i] and orders[j]
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        // Swap orders[i + 1] and orders[high]
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }
}
public class Ex_2_Sorting_Customer_Orders{
    public static void main(String[] args) {
        Order[] orders = {
            new Order("O001", "Alice", 250.75),
            new Order("O002", "Bob", 150.50),
            new Order("O003", "Charlie", 350.00),
            new Order("O004", "Diana", 200.00)
        };

        System.out.println("Original Orders:");
        for (Order order : orders) {
            System.out.println(order);
        }

        // Bubble Sort
        BubbleSort.bubbleSortByTotalPrice(orders);
        System.out.println("\nOrders Sorted by Bubble Sort:");
        for (Order order : orders) {
            System.out.println(order);
        }

        // Reinitialize orders
        orders = new Order[] {
            new Order("O001", "Alice", 250.75),
            new Order("O002", "Bob", 150.50),
            new Order("O003", "Charlie", 350.00),
            new Order("O004", "Diana", 200.00)
        };

        // Quick Sort
        QuickSort.quickSortByTotalPrice(orders, 0, orders.length - 1);
        System.out.println("\nOrders Sorted by Quick Sort:");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
