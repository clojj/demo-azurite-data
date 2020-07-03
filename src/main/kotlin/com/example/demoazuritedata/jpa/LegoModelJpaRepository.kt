package com.example.demoazuritedata.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LegoModelJpaRepository : JpaRepository<LegoModel, Long>
