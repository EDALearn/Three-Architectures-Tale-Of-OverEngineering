package io.zenwave360.example.adapters.web;

import io.zenwave360.example.adapters.web.model.AddressDTO;
import io.zenwave360.example.adapters.web.model.AddressTypeDTO;
import io.zenwave360.example.adapters.web.model.CustomerDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

/**
* Business Flow Test for: createCustomer, updateCustomer, deleteCustomer, getCustomer.
*/
public class CreateUpdateDeleteCustomerIntegrationTest extends BaseWebTestClientTest {

    @Autowired
    EntityManager entityManager;

    /**
    * Business Flow Test for: createCustomer, updateCustomer, deleteCustomer, getCustomer.
    */
    @Test
    public void testCreateUpdateDeleteCustomerIntegrationTest() {
        // createCustomer: Create customer javadoc comment
        CustomerDTO customerRequestBody0 = new CustomerDTO();
        //        requestBody.setId(1L);
        //        requestBody.setVersion(null);
        customerRequestBody0.setUsername("username");
        customerRequestBody0.setEmail("email");
        customerRequestBody0.setAddress(new AddressDTO());
        //        requestBody.getAddress().setId(1L);
        //        requestBody.getAddress().setVersion(1);
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

        var customer = createCustomerResponse0.getResponseBody().blockFirst();
        var customerId = customer.getId();
    
        // updateCustomer: updateCustomer
        customer.getAddress().setType(AddressTypeDTO.WORK);

        var updateCustomerResponse1 = webTestClient.method(PUT).uri("/api/customers/{customerId}", customerId)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(customer)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .returnResult(CustomerDTO.class);

    
        // deleteCustomer: deleteCustomer
        webTestClient.method(DELETE).uri("/api/customers/{customerId}", customerId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(204);

        entityManager.flush();
    
        // getCustomer: getCustomer
        webTestClient.method(GET).uri("/api/customers/{customerId}", customerId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(404);

    }


}
