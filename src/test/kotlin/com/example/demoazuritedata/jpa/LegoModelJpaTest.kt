package com.example.demoazuritedata.jpa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
@ContextConfiguration(initializers = [MssqlContextInitializer::class])
class LegoModelJpaTest(@Autowired val brickJpaRepository: BrickJpaRepository) {

    @Test
    fun modelReferencingBricks() {
        val thin2x2 = Brick(description = "2x2 - thin")
        val thin2x4 = Brick(description = "2x4 - thin")
        val normal2x2 = Brick(description = "2x2 - normal")
        val bricks: List<Brick> = brickJpaRepository.saveAll(listOf(thin2x2, thin2x4, normal2x2)).toList()

        assertThat(bricks).hasSize(3)
        assertThat(brickJpaRepository.findAll().toList().size).isEqualTo(3)
    }

}

class MssqlContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(context: ConfigurableApplicationContext) {
        mssqlContainer.start()
        TestPropertyValues.of(
            "spring.datasource.driver-class-name=${mssqlContainer.driverClassName}",
            "spring.datasource.url=${mssqlContainer.jdbcUrl}",
            "spring.datasource.username=${mssqlContainer.username}",
            "spring.datasource.password=${mssqlContainer.password}"
        )
            .applyTo(context.environment)
    }

    companion object {
        val mssqlContainer: KMssqlContainer = KMssqlContainer("mcr.microsoft.com/mssql/server:2017-latest").withInitScript("schema.sql")
    }

    class KMssqlContainer(imageName: String?) : MSSQLServerContainer<KMssqlContainer>(imageName)
}