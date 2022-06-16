package boot.spring.controller

import boot.spring.entity.User
import boot.spring.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpSession

@Controller
class Login {
    @Autowired
    private lateinit var loginservice: LoginService

    @RequestMapping("/login")
    fun login(): String {
        return "login"
    }

    @RequestMapping("/logout")
    fun logout(httpSession: HttpSession?): String {
        return "login"
    }

    @GetMapping("/currentUser")
    @ResponseBody
    fun currentUser(httpSession: HttpSession): User? {
        return null
    }
}