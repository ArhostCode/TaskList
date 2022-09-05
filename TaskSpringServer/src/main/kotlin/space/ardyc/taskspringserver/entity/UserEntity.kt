package space.ardyc.taskspringserver.entity

import javax.persistence.*

@Entity
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L

    lateinit var login: String
    lateinit var password: String
    lateinit var token: String

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    lateinit var tasks: List<TaskEntity>

}