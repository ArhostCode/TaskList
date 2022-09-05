package space.ardyc.taskspringserver.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import space.ardyc.taskspringserver.service.UserService

@RestController
@RequestMapping("/auth")
class LoginController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/login")
    fun login(@RequestParam name: String, @RequestParam password: String): Any {
        return try {
            ResponseEntity.ok(userService.login(name, password))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/signin")
    fun signin(@RequestParam name: String, @RequestParam password: String): Any {
        return try {
            userService.signin(name, password)
            ResponseEntity.ok("User has been created")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }


}