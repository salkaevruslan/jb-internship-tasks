package org.example.templatemessenger.model

import javax.persistence.*

@Entity
@Table(name = "recipients")
data class Recipient(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(unique = true, nullable = false)
    var name: String = "",
)