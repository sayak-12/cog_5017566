import java.util.*;
class prod {
    private String productId;
    private String productName;
    private String category;

    // Constructor
    public prod(String productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + ", category=" + category + "]";
    }
}
class LinearSearch {
    public static prod linearSearchById(prod[] products, String productId) {
        for (prod product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public static prod linearSearchByName(prod[] products, String productName) {
        for (prod product : products) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public static prod linearSearchByCategory(prod[] products, String category) {
        for (prod product : products) {
            if (product.getCategory().equals(category)) {
                return product;
            }
        }
        return null;
    }
}
class BinarySearch {
    public static prod binarySearchById(prod[] products, String productId) {
        Arrays.sort(products, Comparator.comparing(prod::getProductId));
        int left = 0, right = products.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = products[mid].getProductId().compareTo(productId);
            if (comparison == 0) {
                return products[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static prod binarySearchByName(prod[] products, String productName) {
        Arrays.sort(products, Comparator.comparing(prod::getProductName));
        int left = 0, right = products.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = products[mid].getProductName().compareTo(productName);
            if (comparison == 0) {
                return products[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static prod binarySearchByCategory(prod[] products, String category) {
        Arrays.sort(products, Comparator.comparing(prod::getCategory));
        int left = 0, right = products.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = products[mid].getCategory().compareTo(category);
            if (comparison == 0) {
                return products[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}
public class Ex_2_Ecommerce_Platform_Search_Function{
    public static void main(String[] args) {
    	prod[] products = {
            new prod("P001", "Laptop", "Electronics"),
            new prod("P002", "Smartphone", "Electronics"),
            new prod("P004", "Headphones", "Accessories"),
            new prod("P003", "Tablet", "Electronics"),
            new prod("P005", "Charger", "Accessories")
        };

        // Linear Search
        System.out.println("Linear Search by ID: " + LinearSearch.linearSearchById(products, "P003"));
        System.out.println("Linear Search by Name: " + LinearSearch.linearSearchByName(products, "Headphones"));
        System.out.println("Linear Search by Category: " + LinearSearch.linearSearchByCategory(products, "Accessories"));

        // Binary Search
        System.out.println("Binary Search by ID: " + BinarySearch.binarySearchById(products, "P003"));
        System.out.println("Binary Search by Name: " + BinarySearch.binarySearchByName(products, "Headphones"));
        System.out.println("Binary Search by Category: " + BinarySearch.binarySearchByCategory(products, "Accessories"));
    }
}
