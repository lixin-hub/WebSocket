package boot.spring.service

import boot.spring.entity.User

interface LoginService {
    fun getPasswordByName(name: String?): String?
    fun getUserById(id: Int): User?
    fun register(user: User): Boolean
    fun login(user: User) :User?
}