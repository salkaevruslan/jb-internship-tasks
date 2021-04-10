package org.example.templatemessenger.model

import javax.persistence.*


@Entity
@Table(name = "templates")
data class Template(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(unique = true, nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var template: String = "",
) {
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "template_targets",
        joinColumns = [JoinColumn(name = "template_id")],
        inverseJoinColumns = [JoinColumn(name = "recipient_id")],
    )
    val recipients: MutableSet<Recipient> = HashSet()
}