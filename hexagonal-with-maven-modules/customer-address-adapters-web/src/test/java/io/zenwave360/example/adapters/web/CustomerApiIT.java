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
* Integration tests for the {@link CustomerApi} REST controller.
*/
public class CustomerApiIT extends BaseWebTestClientTest {



    /**
    * Test: Create customer javadoc comment for OK.
    */
    @Test
    public void testCreateCustomer_201() {
        CustomerDTO requestBody = new CustomerDTO();
        requestBody.setId(null);
        requestBody.setVersion(null);
        requestBody.setUsername(null);
        requestBody.setEmail(null);
        requestBody.setAddress(new AddressDTO());
        requestBody.getAddress().setId(1L);
        requestBody.getAddress().setVersion(1);
        requestBody.getAddress().setStreet("aaa");
        requestBody.getAddress().setCity("aaa");
        requestBody.getAddress().setState("aaa");
        requestBody.getAddress().setZip("aaa");
        requestBody.getAddress().setType(AddressTypeDTO.HOME);

        webTestClient.method(POST).uri("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isEqualTo(201)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.version").isNotEmpty()
            .jsonPath("$.username").isNotEmpty()
            .jsonPath("$.email").isNotEmpty()
            .jsonPath("$.address").isNotEmpty()
            .jsonPath("$.address.id").isNotEmpty()
            .jsonPath("$.address.version").isNotEmpty()
            .jsonPath("$.address.street").isNotEmpty()
            .jsonPath("$.address.city").isNotEmpty()
            .jsonPath("$.address.state").isNotEmpty()
            .jsonPath("$.address.zip").isNotEmpty()
            .jsonPath("$.address.type").isNotEmpty();
    }

    /**
    * Test: listCustomers for OK.
    */
    @Test
    public void testListCustomers_200() {
        var search = "";
        var page = "";
        var limit = "";
        var sort = "";

        webTestClient.method(GET).uri(uriBuilder -> uriBuilder.path("/api/customers").queryParam("search", search).queryParam("page", page).queryParam("limit", limit).queryParam("sort", sort).build(search, page, limit, sort))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.number").isNotEmpty()
            .jsonPath("$.numberOfElements").isNotEmpty()
            .jsonPath("$.size").isNotEmpty()
            .jsonPath("$.totalElements").isNotEmpty()
            .jsonPath("$.totalPages").isNotEmpty()
            .jsonPath("$.content").isNotEmpty()
            .jsonPath("$.content").isArray()
            .jsonPath("$.content[0].id").isNotEmpty()
            .jsonPath("$.content[0].version").isNotEmpty()
            .jsonPath("$.content[0].username").isNotEmpty()
            .jsonPath("$.content[0].email").isNotEmpty()
            .jsonPath("$.content[0].address").isNotEmpty();
    }

    /**
    * Test: updateCustomer for OK.
    */
    @Test
    public void testUpdateCustomer_200() {
        CustomerDTO requestBody = new CustomerDTO();
        requestBody.setId(null);
        requestBody.setVersion(null);
        requestBody.setUsername(null);
        requestBody.setEmail(null);
        requestBody.setAddress(new AddressDTO());
        requestBody.getAddress().setId(1L);
        requestBody.getAddress().setVersion(1);
        requestBody.getAddress().setStreet("aaa");
        requestBody.getAddress().setCity("aaa");
        requestBody.getAddress().setState("aaa");
        requestBody.getAddress().setZip("aaa");
        requestBody.getAddress().setType(AddressTypeDTO.HOME);
        var customerId = "";

        webTestClient.method(PUT).uri("/api/customers/{customerId}", customerId)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.version").isNotEmpty()
            .jsonPath("$.username").isNotEmpty()
            .jsonPath("$.email").isNotEmpty()
            .jsonPath("$.address").isNotEmpty()
            .jsonPath("$.address.id").isNotEmpty()
            .jsonPath("$.address.version").isNotEmpty()
            .jsonPath("$.address.street").isNotEmpty()
            .jsonPath("$.address.city").isNotEmpty()
            .jsonPath("$.address.state").isNotEmpty()
            .jsonPath("$.address.zip").isNotEmpty()
            .jsonPath("$.address.type").isNotEmpty();
    }

    /**
    * Test: deleteCustomer for OK.
    */
    @Test
    public void testDeleteCustomer_204() {
        var customerId = "";

        webTestClient.method(DELETE).uri("/api/customers/{customerId}", customerId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(204);
    }

    /**
    * Test: getCustomer for OK.
    */
    @Test
    public void testGetCustomer_200() {
        var customerId = "";

        webTestClient.method(GET).uri("/api/customers/{customerId}", customerId)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.version").isNotEmpty()
            .jsonPath("$.username").isNotEmpty()
            .jsonPath("$.email").isNotEmpty()
            .jsonPath("$.address").isNotEmpty()
            .jsonPath("$.address.id").isNotEmpty()
            .jsonPath("$.address.version").isNotEmpty()
            .jsonPath("$.address.street").isNotEmpty()
            .jsonPath("$.address.city").isNotEmpty()
            .jsonPath("$.address.state").isNotEmpty()
            .jsonPath("$.address.zip").isNotEmpty()
            .jsonPath("$.address.type").isNotEmpty();
    }

    /**
    * Test: Updates a the customer address identified by address.identifier for OK.
    */
    @Test
    public void testUpdateCustomerAddress_200() {
        AddressDTO requestBody = new AddressDTO();
        requestBody.setId(null);
        requestBody.setVersion(null);
        requestBody.setStreet(null);
        requestBody.setCity(null);
        requestBody.setState(null);
        requestBody.setZip(null);
        requestBody.setType(null);
        var customerId = "";

        webTestClient.method(PUT).uri("/api/customers/{customerId}/address", customerId)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.version").isNotEmpty()
            .jsonPath("$.username").isNotEmpty()
            .jsonPath("$.email").isNotEmpty()
            .jsonPath("$.address").isNotEmpty()
            .jsonPath("$.address.id").isNotEmpty()
            .jsonPath("$.address.version").isNotEmpty()
            .jsonPath("$.address.street").isNotEmpty()
            .jsonPath("$.address.city").isNotEmpty()
            .jsonPath("$.address.state").isNotEmpty()
            .jsonPath("$.address.zip").isNotEmpty()
            .jsonPath("$.address.type").isNotEmpty();
    }

}