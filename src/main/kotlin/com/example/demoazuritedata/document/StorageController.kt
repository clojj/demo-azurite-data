package com.example.demoazuritedata.document

import com.azure.storage.blob.models.BlobContainerProperties
import com.azure.storage.blob.models.BlobProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class StorageController(private val azureBlobStorage: AzureBlobStorage, private val documentService: DocumentService) {

    @PutMapping("containers/{containerName}/{blobName}", consumes = ["text/plain"])
    fun postContent(@PathVariable containerName: String, @PathVariable blobName: String, @RequestBody content: String): ResponseEntity<String> {
        azureBlobStorage.createOrGetContainer(containerName).uploadText(blobName, content)
        return ResponseEntity.ok("")
    }

    @PutMapping("documents/{containerName}/{blobName}", consumes = ["text/plain"])
    fun postDocument(@PathVariable containerName: String, @PathVariable blobName: String, @RequestBody content: String): ResponseEntity<String> {
        documentService.saveDocument(Document(null, EmbeddedBlob(containerName, blobName), null, null), content)
        return ResponseEntity.ok("")
    }

    @GetMapping("containers/{containerName}/{blobName}", produces = ["text/plain"])
    fun getContent(@PathVariable containerName: String, @PathVariable blobName: String) =
        azureBlobStorage.createOrGetContainer(containerName).getText(blobName)

    @GetMapping("containers/{containerName}/properties")
    fun getProperties(@PathVariable containerName: String): BlobContainerProperties =
        azureBlobStorage.createOrGetContainer(containerName).properties

    @GetMapping("containers/{containerName}/{blobName}/properties")
    fun getBlobProperties(@PathVariable containerName: String, @PathVariable blobName: String): BlobProperties =
        azureBlobStorage.createOrGetContainer(containerName).getBlobProperties(blobName)

}
