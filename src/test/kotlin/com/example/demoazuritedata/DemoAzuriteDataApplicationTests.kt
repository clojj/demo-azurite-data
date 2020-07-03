package com.example.demoazuritedata

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoAzuriteDataApplicationTests(@Autowired private val restTemplate: TestRestTemplate) {

    @Test
    fun testGracefulShutdown() {
        (1..10).forEach { restTemplate.getForObject<String>("/test/$it") == "done: $it" }
    }

}
