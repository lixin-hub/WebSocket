package boot.spring.mapper

import boot.spring.entity.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface LoginMapper {
    fun getUserByName(name: String?): User?
    fun getNameById(id: Int?): User?
    fun saveUser(@Param("user") user: User): Int
}