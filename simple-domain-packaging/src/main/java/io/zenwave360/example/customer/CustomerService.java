package io.zenwave360.example.customer;

import io.zenwave360.example.customer.dtos.*;
import io.zenwave360.example.customer.model.*;
import java.math.*;
import java.time.*;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Inbound Service Port for managing [Customer]. */
public interface CustomerService {

	/**
	 * Create customer javadoc comment
	 *
	 * <p>
	 * With Events: [CustomerEvent].
	 */
	public Customer createCustomer(Customer input);

	/** With Events: [CustomerEvent]. */
	public Optional<Customer> updateCustomer(Long id, Customer input);

	/**
	 * Updates a the customer address identified by address.identifier
	 *
	 * <p>
	 * With Events: [CustomerEvent, CustomerAddressUpdated].
	 */
	public Optional<Customer> updateCustomerAddress(Long id, Address input);

	/** With Events: [CustomerEvent]. */
	public void deleteCustomer(Long id);

	/** */
	public Optional<Customer> getCustomer(Long id);

	/** */
	public Page<Customer> listCustomers(Pageable pageable);

}
