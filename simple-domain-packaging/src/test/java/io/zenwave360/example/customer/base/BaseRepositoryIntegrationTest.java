package io.zenwave360.example.customer.base;

import io.zenwave360.example.customer.config.DockerComposeInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@org.springframework.transaction.annotation.Transactional
@DockerComposeInitializer.EnableDockerCompose
public abstract class BaseRepositoryIntegrationTest {

}
