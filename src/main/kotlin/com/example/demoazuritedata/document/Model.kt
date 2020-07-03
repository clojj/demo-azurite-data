package com.example.demoazuritedata.document

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(schema = "DEMO")
class Document(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, // TODO custom UUID generator
    var blob: EmbeddedBlob,
    @Column(name = "CONTENTMD5")
    var contentMd5: String?,
    var etag: String?
)

@Embeddable
data class EmbeddedBlob(@Column(name = "CONTAINERNAME") var containerName: String, @Column(name = "BLOBNAME") var blobName: String)

