package io.zenwave360.example.adapters.web;

import io.zenwave360.example.adapters.web.*;
import io.zenwave360.example.adapters.web.model.*;
import io.zenwave360.example.adapters.web.BaseWebTestClientTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.springframework.http.HttpMethod.*;

/**
* Business Flow Test for: createCustomer, updateCustomer, deleteCustomer, getCustomer.
*/
public class CreateUpdateDeleteCustomerIntegrationTest extends BaseWebTestClientTest {

    /**
    * Business Flow Test for: createCustomer, updateCustomer, deleteCustomer, getCustomer.
    */
    @Test
    public void testCreateUpdateDeleteCustomerIntegrationTest() {
        // createCustomer: Create customer javadoc comment
        CustomerDTO customerRequestBody0 = new CustomerDTO();
        customerRequestBody0.setId(null);
        customerRequestBody0.setVersion(null);
        customerRequestBody0.setUsername(null);
        customerRequestBody0.setEmail(null);
        customerRequestBody0.setAddress(new AddressDTO());
        customerRequestBody0.getAddress().setId(1L);
        customerRequestBody0.getAddress().setVersion(1);
        customerRequestBody0.getAddress().setStreet("aaa");
        customerRequestBody0.getAddress().setCity("aaa");
        customerRequestBody0.getAddress().setState("aaa");
        customerRequestBody0.getAddress().setZip("aaa");
        customerRequestBody0.getAddress().setType(AddressTypeDTO.HOME);

        var createCustomerResponse0 = webTestClient.method(POST).uri("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(customerRequestBody0)
            .exchange()
            .expectStatus().isEqualTo(201)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .returnResult(CustomerDTO.class);

    
        // updateCustomer: updateCustomer
        CustomerDTO customerRequestBody1 = new CustomerDTO();
        customerRequestBody1.setId(null);
        customerRequestBody1.setVersion(null);
        customerRequestBody1.setUsername(null);
        customerRequestBody1.setEmail(null);
        customerRequestBody1.setAddress(new AddressDTO());
        customerRequestBody1.getAddress().setId(1L);
        customerRequestBody1.getAddress().setVersion(1);
        customerRequestBody1.getAddress().setStreet("aaa");
        customerRequestBody1.getAddress().setCity("aaa");
        customerRequestBody1.getAddress().setState("aaa");
        customerRequestBody1.getAddress().setZip("aaa");
        customerRequestBody1.getAddress().setType(AddressTypeDTO.HOME);
        var customerId1 = "";

        var updateCustomerResponse1 = webTestClient.method(PUT).uri("/api/customers/{customerId}", customerId1)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(customerRequestBody1)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .returnResult(CustomerDTO.class);

    
        // deleteCustomer: deleteCustomer
        var customerId2 = "";

        webTestClient.method(DELETE).uri("/api/customers/{customerId}", customerId2)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(204);

    
        // getCustomer: getCustomer
        var customerId3 = "";

        var getCustomerResponse3 = webTestClient.method(GET).uri("/api/customers/{customerId}", customerId3)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .returnResult(CustomerDTO.class);

    
    }


}
