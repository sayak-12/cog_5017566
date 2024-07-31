import java.util.HashMap;
import java.util.Map;
class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String toString() {
        return "Product [productId=" + productId + ", productName=" + productName + 
               ", quantity=" + quantity + ", price=" + price + "]";
    }
}
class Inventory {
    private Map<String, Product> products;
    public Inventory() {
        products = new HashMap<>();
    }

    // Method to add a product
    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
        System.out.println("Product added: " + product);
    }
    public void updateProduct(Product product) {
        if (products.containsKey(product.getProductId())) {
            products.put(product.getProductId(), product);
            System.out.println("Product updated: " + product);
        } else {
            System.out.println("Product with ID " + product.getProductId() + " does not exist.");
        }
    }
    public void deleteProduct(String productId) {
        Product removedProduct = products.remove(productId);
        if (removedProduct != null) {
            System.out.println("Product removed: " + removedProduct);
        } else {
            System.out.println("Product with ID " + productId + " does not exist.");
        }
    }
    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            for (Product product : products.values()) {
                System.out.println(product);
            }
        }
    }
}

public class Ex_1_Inventory_Management_System {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Product product1 = new Product("P001", "Laptop", 10, 999.99);
        Product product2 = new Product("P002", "Smartphone", 20, 499.99);
        Product product3 = new Product("P003", "Tablet", 15, 299.99);
        inventory.addProduct(product1);
        inventory.addProduct(product2);
        inventory.addProduct(product3);
        inventory.displayAllProducts();
        // Update a product
        Product updatedProduct1 = new Product("P001", "Laptop", 12, 979.99);
        inventory.updateProduct(updatedProduct1);
        // Delete a product
        inventory.deleteProduct("P002");
        inventory.displayAllProducts();
    }
}
