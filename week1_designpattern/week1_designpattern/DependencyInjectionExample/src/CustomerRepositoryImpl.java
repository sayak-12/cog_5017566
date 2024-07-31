public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public String findCustomerById(int id) {
        // Simulating a database lookup
        return "Customer Name for ID " + id;
    }
}
