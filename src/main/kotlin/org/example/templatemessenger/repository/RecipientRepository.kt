package org.example.templatemessenger.repository

import org.example.templatemessenger.model.Recipient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
interface RecipientRepository : JpaRepository<Recipient, Long> {
    fun findByName(name: String): Optional<Recipient>
}