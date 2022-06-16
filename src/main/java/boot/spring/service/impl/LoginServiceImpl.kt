package boot.spring.service.impl


import boot.spring.entity.User
import boot.spring.mapper.LoginMapper
import boot.spring.service.LoginService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 5)
@Service("loginservice")
open class LoginServiceImpl : LoginService {
    @Resource
    var loginMapper: LoginMapper? = null

    override fun getPasswordByName(name: String?): String? =
        loginMapper!!.getUserByName(name)?.password

    override fun getUserById(id: Int): User? =
        loginMapper!!.getNameById(id)

    override fun register(user: User): Boolean {
        loginMapper!!.getUserByName(user.username)?.let {
            return false
        }
        loginMapper!!.saveUser(user)
        return true
    }

    override fun login(user: User): User? {
        loginMapper!!.getUserByName(user.username)?.let {
            if (it.password == user.password) {
                return it
            }
        }
        return null
    }
}