package io.zenwave360.example.config;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class DockerComposeInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private Logger log = LoggerFactory.getLogger(getClass());

	/** Use this annotation to activate TestContainers in your test. */
	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@org.springframework.test.context.ContextConfiguration(initializers = DockerComposeInitializer.class)
	public @interface EnableDockerCompose {

	}

	private record Service(String name, int port, String envVar, String envValueTemplate) {
	}

	private static final String DOCKER_COMPOSE_FILE = "./docker-compose.yml";

	private static final List<Service> SERVICES = List.of(
			new Service("postgresql", 5432, "DATASOURCE_URL", "jdbc:postgresql://%s:%s/DATABASENAME"),
			new Service("kafka", 9092, "KAFKA_BOOTSTRAP_SERVERS", "%s:%s"));

	static String HOST = DockerClientFactory.instance().dockerHostIpAddress();
	static DockerComposeContainer container = new DockerComposeContainer(new File(DOCKER_COMPOSE_FILE)).withEnv("HOST",
			HOST);

	static {
		for (Service service : SERVICES) {
			container.withExposedService(service.name, service.port, Wait.forListeningPort());
		}
		// .withExposedService("schema-registry", 8081,
		// Wait.forHttp("/subjects").forStatusCode(200))
	}

	static boolean isContainerRunning = false;

	@SneakyThrows
	@Override
	public void initialize(ConfigurableApplicationContext ctx) {
		if (isDockerComposeRunningAllServices(SERVICES.size())) {
			log.info("Docker Compose Containers are running from local docker-compose. Skipping TestContainers...");
		}
		else {
			log.info(
					"Docker Compose Containers are not running from local docker-compose. Starting from TestContainers...");
			if (isContainerRunning) {
				log.info("Docker Compose Containers are already running from TestContainers. Skipping...");
			}
			else {
				log.info("Starting Docker Compose Containers from TestContainers...");
				container.start();
				isContainerRunning = true;

				for (Service service : SERVICES) {
					int port = container.getServicePort(service.name, service.port);
					log.info("DockerCompose exposed port for {}: {}", service.name, HOST + ":" + port);
					log.info("DockerCompose Service {} listening: {}", service.name, isPortOpen(HOST, port));
					if (service.envValueTemplate != null) {
						TestPropertyValues
							.of(service.envVar + "=" + String.format(service.envValueTemplate, HOST, port))
							.applyTo(ctx.getEnvironment());
					}
				}
			}
		}
	}

	private boolean isDockerComposeRunningAllServices(int numberOfServices) {
		return Stream.of("docker-compose", "docker-compose.exe").anyMatch(cmd -> {
			try {
				return readProcessOutputStream(cmd, "-f", "src/main/docker/docker-compose.yml", "ps")
					.size() == (numberOfServices + 1);
			}
			catch (IOException | InterruptedException e) {
				return false;
			}
		});
	}

	private List<String> readProcessOutputStream(String... command) throws IOException, InterruptedException {
		var process = new ProcessBuilder(command).start();
		var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
		var line = "";
		var output = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			output.add(line);
		}
		process.waitFor();
		return output;
	}

	boolean isPortOpen(String host, int port) {
		try (Socket socket = new Socket(host, port)) {
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}

}
