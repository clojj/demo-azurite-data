package com.example.demoazuritedata.jpa

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ControllerJpa(private val brickJpaRepository: BrickJpaRepository, private val legoModelJpaRepository: LegoModelJpaRepository) {

    @PostMapping("jpa/brick")
    fun createBrickJpa(@RequestBody description: String) {
        brickJpaRepository.save(Brick(description = description))
    }

    @PostMapping("jpa/legomodel")
    fun createBrickJpa(@RequestBody legoModelDto: LegoModelDto) {
        with(legoModelDto) {
            val legoModel = LegoModel(name = name)
            legoModel.brickContent = setOf(BrickContentItem(legoModel = legoModel, brickId = brickId, amount = amount))
            legoModelJpaRepository.save(legoModel)
        }
    }

    // TODO map entity to dto

    @GetMapping("jpa/bricks")
    fun allBricksJpa(): MutableList<Brick> {
        return brickJpaRepository.findAll()
    }

}

data class LegoModelDto(val name: String, val brickId: Long, val amount: Int)
