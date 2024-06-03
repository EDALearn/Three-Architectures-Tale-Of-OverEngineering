package io.zenwave360.example.customer;

import io.zenwave360.example.customer.dtos.*;
import io.zenwave360.example.customer.events.*;
import io.zenwave360.example.customer.mappers.*;
import io.zenwave360.example.customer.model.*;
import java.math.*;
import java.time.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service Implementation for managing [Customer]. */
@Service
@Transactional(readOnly = true)
@lombok.AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final CustomerServiceMapper customerServiceMapper = CustomerServiceMapper.INSTANCE;

	private final CustomerRepository customerRepository;

	private final EventsMapper eventsMapper = EventsMapper.INSTANCE;

	private final ICustomerEventsProducer eventsProducer;

	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public Customer createCustomer(Customer input) {
		log.debug("[CRUD] Request to save Customer: {}", input);
		var customer = customerServiceMapper.update(new Customer(), input);
		customer = customerRepository.save(customer);
		// TODO: you may need to reload the entity here to fetch relationships 'mapped by
		// id'
		// emit events
		var customerEvent = eventsMapper.asCustomerEvent(customer);
		eventsProducer.onCustomerEvent(customerEvent);
		return customer;
	}

	@Transactional
	public Optional<Customer> updateCustomer(Long id, Customer input) {
		log.debug("Request updateCustomer: {} {}", id, input);

		var customer = customerRepository.findById(id).map(existingCustomer -> {
			return customerServiceMapper.update(existingCustomer, input);
		})
		.map(customerRepository::save);
		if (customer.isPresent()) {
			// emit events
			var customerEvent = eventsMapper.asCustomerEvent(customer.get());
			eventsProducer.onCustomerEvent(customerEvent);
		}
		return customer;
	}

	@Transactional
	public Optional<Customer> updateCustomerAddress(Long id, Address input) {
		log.debug("Request updateCustomerAddress: {} {}", id, input);

		var customer = customerRepository.findById(id).map(existingCustomer -> {
			customerServiceMapper.update(existingCustomer.getAddress(), input);
			return existingCustomer;
		})
		.map(customerRepository::save);
		if (customer.isPresent()) {
			// emit events
			var customerEvent = eventsMapper.asCustomerEvent(customer.get());
			eventsProducer.onCustomerEvent(customerEvent);
			var customerAddressUpdated = eventsMapper.asCustomerAddressUpdated(customer.get());
			eventsProducer.onCustomerAddressUpdated(customerAddressUpdated);
		}
		return customer;
	}

	@Transactional
	public void deleteCustomer(Long id) {
		log.debug("[CRUD] Request to delete Customer : {}", id);
		customerRepository.deleteById(id);
		// emit events
		var customerEvent = eventsMapper.asCustomerEvent(id);
		eventsProducer.onCustomerEvent(customerEvent);
	}

	public Optional<Customer> getCustomer(Long id) {
		log.debug("[CRUD] Request to get Customer : {}", id);
		var customer = customerRepository.findById(id);
		return customer;
	}

	public Page<Customer> listCustomers(Pageable pageable) {
		log.debug("Request listCustomers: {}", pageable);

		var customers = customerRepository.findAll(pageable);
		return customers;
	}

}
