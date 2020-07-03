package com.example.demoazuritedata

import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.BlobContainerClientBuilder
import com.azure.storage.blob.models.BlobContainerProperties
import com.azure.storage.blob.models.BlobProperties
import com.example.demoazuritedata.document.AzureBlobStorage
import com.example.demoazuritedata.document.AzureStorageProperties
import com.example.demoazuritedata.jpa.Brick
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.io.ByteArrayInputStream

@SpringBootApplication
@EnableConfigurationProperties(value = [AzureStorageProperties::class])
class DemoAzuriteDataApplication

fun main(args: Array<String>) {
    runApplication<DemoAzuriteDataApplication>(*args)
}

@RestController
class TestController {

    @GetMapping("test/{counter}")
    fun test(@PathVariable counter: Int): String {
        Thread.sleep(1000)
        return "done: $counter"
    }
}