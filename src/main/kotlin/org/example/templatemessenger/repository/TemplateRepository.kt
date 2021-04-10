package org.example.templatemessenger.repository

import org.example.templatemessenger.model.Template
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Repository
@Transactional
interface TemplateRepository : JpaRepository<Template, Long> {
    fun findByName(name: String): Optional<Template>
}