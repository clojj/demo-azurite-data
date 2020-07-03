package com.example.demoazuritedata.document

import com.azure.core.util.Base64Util
import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.BlobContainerClientBuilder
import com.azure.storage.blob.models.BlobProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayInputStream

@Service
class DocumentService(val documentJpaRepository: DocumentJpaRepository, val azureBlobStorage: AzureBlobStorage) {

    @Transactional
    fun saveDocument(document: Document, content: String) {
        with(document.blob) {
            val result = kotlin.runCatching {
                azureBlobStorage.createOrGetContainer(containerName).uploadText(blobName, content)
            }
            result.fold({
                val blobProperties = azureBlobStorage.createOrGetContainer(containerName).getBlobProperties(blobName)
                document.contentMd5 = Base64Util.encodeToString(blobProperties.contentMd5)
                document.etag = blobProperties.eTag
                documentJpaRepository.save(document)

            }) {
                println(it.message) // TODO parse XML detailed message
            }
        }
    }
}

@ConfigurationProperties(prefix = "azure")
@ConstructorBinding
data class AzureStorageProperties(val storage: Storage) {
    data class Storage(val defaultEndpointsProtocol: String, val accountName: String, val accountKey: String, val blobEndpoint: String)
}

// TODO Storage REST API with WebClient

@Service
class AzureBlobStorage(azureStorageProperties: AzureStorageProperties) {

    val connectionString: String

    init {
        with(azureStorageProperties.storage) {
            connectionString = "DefaultEndpointsProtocol=${defaultEndpointsProtocol};AccountName=${accountName};AccountKey=${accountKey};BlobEndpoint=${blobEndpoint};"
        }
    }

    fun createOrGetContainer(containerName: String): BlobContainerClient {
        val containerClient = BlobContainerClientBuilder()
            .connectionString(connectionString)
            .containerName(containerName)
            .buildClient()
        if (!containerClient.exists()) {
            containerClient.create()
        }
        return containerClient
    }
}

fun BlobContainerClient.uploadText(blobName: String, text: String) {
    val blobClient = getBlobClient(blobName)
    val content = text.toByteArray()
    setMetadata(mapOf("validUntil" to "2020-12-31"))
    blobClient.upload(ByteArrayInputStream(content), content.size.toLong())
}

fun BlobContainerClient.getText(blobName: String) =
    getBlobClient(blobName).openInputStream().use { String(it.readAllBytes()) }

fun BlobContainerClient.getBlobProperties(blobName: String): BlobProperties =
    getBlobClient(blobName).properties
