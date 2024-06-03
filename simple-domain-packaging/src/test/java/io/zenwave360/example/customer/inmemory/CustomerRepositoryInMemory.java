package io.zenwave360.example.customer.inmemory;

import io.zenwave360.example.customer.CustomerRepository;
import io.zenwave360.example.customer.model.*;

public class CustomerRepositoryInMemory extends InMemoryJpaRepository<Customer> implements CustomerRepository {

}
