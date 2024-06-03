package io.zenwave360.example.adapters.web;

import io.zenwave360.example.adapters.web.mappers.*;
import io.zenwave360.example.adapters.web.model.*;
import io.zenwave360.example.core.domain.*;
import io.zenwave360.example.core.inbound.*;
import io.zenwave360.example.core.inbound.dtos.*;
import java.math.*;
import java.time.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

/** REST controller for CustomerApi. */
@RestController
@RequestMapping("/api")
public class CustomerApiController implements CustomerApi {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private NativeWebRequest request;

	private CustomerService customerService;

	@Autowired
	public CustomerApiController setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
		return this;
	}

	private CustomerDTOsMapper mapper = CustomerDTOsMapper.INSTANCE;

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.ofNullable(request);
	}

	@Override
	public ResponseEntity<CustomerDTO> createCustomer(CustomerDTO reqBody) {
		var input = mapper.asCustomer(reqBody);
		var customer = customerService.createCustomer(input);
		CustomerDTO responseDTO = mapper.asCustomerDTO(customer);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@Override
	public ResponseEntity<CustomerPaginatedDTO> listCustomers(Optional<String> search, Optional<Integer> page,
			Optional<Integer> limit, Optional<List<String>> sort) {
		var customerPage = customerService.listCustomers(pageOf(page, limit, sort));
		var responseDTO = mapper.asCustomerPaginatedDTO(customerPage);
		return ResponseEntity.status(200).body(responseDTO);
	}

	@Override
	public ResponseEntity<CustomerDTO> updateCustomer(Long customerId, CustomerDTO reqBody) {
		var input = mapper.asCustomer(reqBody);
		var customer = customerService.updateCustomer(customerId, input);
		if (customer.isPresent()) {
			CustomerDTO responseDTO = mapper.asCustomerDTO(customer.get());
			return ResponseEntity.status(200).body(responseDTO);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<Void> deleteCustomer(Long customerId) {
		customerService.deleteCustomer(customerId);
		return ResponseEntity.status(204).build();
	}

	@Override
	public ResponseEntity<CustomerDTO> getCustomer(Long customerId) {
		var customer = customerService.getCustomer(customerId);
		if (customer.isPresent()) {
			CustomerDTO responseDTO = mapper.asCustomerDTO(customer.get());
			return ResponseEntity.status(200).body(responseDTO);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<CustomerDTO> updateCustomerAddress(Long customerId, AddressDTO reqBody) {
		var input = mapper.asAddress(reqBody);
		var customer = customerService.updateCustomerAddress(customerId, input);
		if (customer.isPresent()) {
			CustomerDTO responseDTO = mapper.asCustomerDTO(customer.get());
			return ResponseEntity.status(200).body(responseDTO);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	protected Pageable pageOf(Optional<Integer> page, Optional<Integer> limit, Optional<List<String>> sort) {
		return PageRequest.of(page.orElse(0), limit.orElse(10));
	}

}
