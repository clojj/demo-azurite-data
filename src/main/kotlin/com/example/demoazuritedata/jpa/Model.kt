package com.example.demoazuritedata.jpa

import java.io.Serializable
import javax.persistence.*


@Entity
@Table(name = "BRICK", schema = "LEGOMODEL")
class Brick(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, // TODO custom UUID generator

    var description: String
)

@Entity
@Table(name = "LEGO_MODEL", schema = "LEGOMODEL")
class LegoModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

    @OneToMany(
        mappedBy = "legoModel",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var brickContent: Set<BrickContentItem>? = mutableSetOf()
)

// TODO composite primary key: https://gist.github.com/mchlstckl/4f9602b5d776878f48f0

@Entity
@Table(name = "BRICKCONTENT", schema = "LEGOMODEL")
class BrickContentItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEGOMODEL_ID")
    var legoModel: LegoModel,

    var brickId: Long,

    var amount: Int

) : Serializable
