package com.example.demo;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"spring.main.web-application-type=servlet",
				"management.info.env.enabled=true",
				"management.endpoints.web.discovery.enabled=true",
				"management.endpoints.web.exposure.include=health,info,env,bindings",
				"info.name=MY TEST APP"
		})
@DirtiesContext
class DemoApplicationTests {

	@Autowired
	protected TestRestTemplate restTemplate;

	@SpringBootApplication
	@Import(TestChannelBinderConfiguration.class)
	public static class AutoConfigurationApplication {
		@Bean
		Consumer<String> upper() {
			return s -> s.toUpperCase();
		}
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testBindingsEndpoint() {
		ResponseEntity<List> response = this.restTemplate.getForEntity("/actuator/bindings", List.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.hasBody()).isTrue();
	}

}
