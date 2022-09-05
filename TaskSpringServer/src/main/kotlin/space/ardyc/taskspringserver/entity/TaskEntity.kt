package space.ardyc.taskspringserver.entity

import javax.persistence.*

@Entity
class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L

    lateinit var name: String
    lateinit var text: String
    var completed: Boolean = false

    @ManyToOne
    lateinit var user: UserEntity
}