package io.zenwave360.example.adapters.web;

import io.zenwave360.example.adapters.web.model.AddressDTO;
import io.zenwave360.example.adapters.web.model.AddressTypeDTO;
import io.zenwave360.example.adapters.web.model.CustomerDTO;
import io.zenwave360.example.config.ServicesInMemoryConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/** Test controller for CustomerApiController. */
public class CustomerApiControllerTest {

	private final Logger log = LoggerFactory.getLogger(getClass());

	ServicesInMemoryConfig context = new ServicesInMemoryConfig();

	CustomerApiController controller = new CustomerApiController().setCustomerService(context.customerService());

	@BeforeEach
	void setUp() {
		context.reloadTestData();
	}

	@Test
	public void createCustomerTest() {
		CustomerDTO reqBody = null;
		var response = controller.createCustomer(reqBody);
		Assertions.assertEquals(201, response.getStatusCode().value());
	}

	@Test
	public void listCustomersTest() {
		Optional<String> search = Optional.empty();
		Optional<Integer> page = Optional.of(0);
		Optional<Integer> limit = Optional.of(10);
		Optional<List<String>> sort = Optional.empty();
		var response = controller.listCustomers(search, page, limit, sort);
		Assertions.assertEquals(200, response.getStatusCode().value());
	}

	@Test
	public void updateCustomerTest() {
		Long customerId = 1L;
		CustomerDTO reqBody = new CustomerDTO("name", "email")
				.address(new AddressDTO("street", AddressTypeDTO.HOME));
		var response = controller.updateCustomer(customerId, reqBody);
		Assertions.assertEquals(200, response.getStatusCode().value());
	}

	@Test
	public void deleteCustomerTest() {
		Long customerId = 1L;
		var response = controller.deleteCustomer(customerId);
		Assertions.assertEquals(204, response.getStatusCode().value());
	}

	@Test
	public void getCustomerTest() {
		Long customerId = 1L;
		var response = controller.getCustomer(customerId);
		Assertions.assertEquals(200, response.getStatusCode().value());
	}

	@Test
	public void updateCustomerAddressTest() {
		Long customerId = 1L;
		AddressDTO reqBody = new AddressDTO("street", AddressTypeDTO.HOME);
		var response = controller.updateCustomerAddress(customerId, reqBody);
		Assertions.assertEquals(200, response.getStatusCode().value());
	}
}
