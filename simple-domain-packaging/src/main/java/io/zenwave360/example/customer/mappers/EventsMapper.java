package io.zenwave360.example.customer.mappers;

import io.zenwave360.example.customer.dtos.*;
import io.zenwave360.example.customer.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { BaseMapper.class })
public interface EventsMapper {

	EventsMapper INSTANCE = Mappers.getMapper(EventsMapper.class);

	io.zenwave360.example.customer.events.dtos.Address asAddress(Address address);

	io.zenwave360.example.customer.events.dtos.CustomerEvent asCustomerEvent(Customer customer);

	io.zenwave360.example.customer.events.dtos.Customer asCustomer(Customer customer);

	io.zenwave360.example.customer.events.dtos.CustomerEvent asCustomerEvent(Long id);

	io.zenwave360.example.customer.events.dtos.CustomerAddressUpdated asCustomerAddressUpdated(Customer customer);

}
