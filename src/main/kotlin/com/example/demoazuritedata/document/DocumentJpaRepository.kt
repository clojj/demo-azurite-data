package com.example.demoazuritedata.document

import com.example.demoazuritedata.jpa.LegoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentJpaRepository : JpaRepository<Document, Long>
