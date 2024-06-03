package io.zenwave360.example.customer.mappers;

import io.zenwave360.example.customer.dtos.*;
import io.zenwave360.example.customer.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { BaseMapper.class })
public interface CustomerServiceMapper {

	CustomerServiceMapper INSTANCE = Mappers.getMapper(CustomerServiceMapper.class);

	// Customer asCustomer(Address input);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "customer", ignore = true)
	Address update(@MappingTarget Address entity, Address input);

	@Mapping(target = "id", ignore = true)
	Customer update(@MappingTarget Customer entity, Customer input);

}
