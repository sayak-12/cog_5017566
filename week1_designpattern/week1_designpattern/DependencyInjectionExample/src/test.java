public class test {
    public static void main(String[] args) {
        // Create the repository
        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        // Inject the repository into the service
        CustomerService customerService = new CustomerService(customerRepository);

        // Use the service to find a customer
        String customerName = customerService.getCustomerName(1);
        System.out.println(customerName);  // Output: Customer Name for ID 1
    }
}
