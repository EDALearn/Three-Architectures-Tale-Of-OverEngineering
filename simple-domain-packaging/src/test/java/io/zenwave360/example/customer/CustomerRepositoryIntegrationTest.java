package io.zenwave360.example.customer;

import io.zenwave360.example.customer.base.BaseRepositoryIntegrationTest;
import io.zenwave360.example.customer.model.Address;
import io.zenwave360.example.customer.model.AddressType;
import io.zenwave360.example.customer.model.Customer;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerRepositoryIntegrationTest extends BaseRepositoryIntegrationTest {

	@Autowired
	EntityManager entityManager;

	@Autowired
	CustomerRepository customerRepository;

	@Test
	public void findAllTest() {
		var results = customerRepository.findAll();
		Assertions.assertFalse(results.isEmpty());
	}

	@Test
	public void findByIdTest() {
		var id = 1L;
		var customer = customerRepository.findById(id).orElseThrow();
		Assertions.assertTrue(customer.getId() != null);
		Assertions.assertTrue(customer.getVersion() != null);
	}

	@Test
	public void saveTest() {
		var random = RandomUtils.nextInt(0, 10000);
		Customer customer = new Customer();
		customer.setUsername("username" + random);
		customer.setEmail("username" + random + "@example.com");

		// OneToOne address owner: true
		var addressId = 1L;
		var address = new Address();
		address.setStreet("");
		address.setCity("");
		address.setState("");
		address.setZip("");
		address.setType(AddressType.values()[0]);
		customer.setAddress(address);

		// Persist aggregate root
		var created = customerRepository.save(customer);

		entityManager.flush();
		entityManager.refresh(created); // reloading to get relationships persisted by id
		Assertions.assertTrue(created.getId() != null);
		Assertions.assertTrue(created.getVersion() != null);

		Assertions.assertTrue(customer.getAddress().getId() != null);
	}

	@Test
	public void updateTest() {
		var id = 1L;
		var customer = customerRepository.findById(id).orElseThrow();
		customer.setUsername("");
		customer.setEmail("");

		customer = customerRepository.save(customer);
		Assertions.assertEquals("", customer.getUsername());
		Assertions.assertEquals("", customer.getEmail());
	}

	@Test
	public void deleteTest() {
		var id = 1L;
		customerRepository.deleteById(id);
		var notFound = customerRepository.findById(id);
		Assertions.assertFalse(notFound.isPresent());
	}

}
