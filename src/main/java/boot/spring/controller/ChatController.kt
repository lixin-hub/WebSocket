package boot.spring.controller

import boot.spring.entity.TResult
import boot.spring.entity.User
import boot.spring.service.LoginService
import boot.spring.service.WebSocketServer.Companion.sessionPools
import boot.spring.service.impl.MessageService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RequestMapping("/api")
@RestController
class ChatController {
    private val logger = LoggerFactory.getLogger(ChatController::class.java)

    @Autowired
    var loginService: LoginService? = null
    @Autowired
    var messageService: MessageService? = null

    @RequestMapping("/onLineUsers")
    @ResponseBody
    fun onLineUsers(@RequestParam("currentUsers") currentUsers: Int): Set<Int> {
        val map = sessionPools
        val nameSet: MutableSet<Int> = HashSet()
        map.forEach {
            if (it.key != currentUsers) nameSet.add(it.key)
        }
        return nameSet
    }

    @PostMapping("register")
    @ResponseBody
    fun register(@RequestBody user: User) :Any{
        println("register user:$user")
        loginService!!.register(user).let {
            if (it) {
                return TResult(200, "注册成功")
            } else {
            return TResult(400, "注册失败")
            }
        }
    }
    @PostMapping("login")
    @ResponseBody
    fun login(@RequestBody user: User,request:HttpServletRequest) :Any{
        // TODO: 2022/6/14 session 验证暂时不写 
        request.session.let { 
            
        }
        println("login user:$user")
        val login = loginService!!.login(user)
        return TResult(if (login!=null) 200 else 400,if (login!=null) "登录成功" else "登录失败,密码或用户名错误",login)
    }
    @GetMapping("historyMessage")
    @ResponseBody
    fun historyMessage(@RequestParam("userId") userId: Int,@RequestParam("friendId") friendId:Int) :Any{
        println("historyMessage userName:$userId friendId:$friendId")
        return TResult(200, "获取历史消息成功",messageService!!.historyMessage(userId,friendId))
    }
    @GetMapping("getMainMessage/{userId}")
    @ResponseBody
    fun getMainMessage(@PathVariable("userId") userId: Int) :Any{
        println("getMainMessage userName:$userId")
        return TResult(200, "获取历史消息成功",messageService!!.getMainMessage(userId))
    }
    
}