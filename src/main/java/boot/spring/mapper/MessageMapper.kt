package boot.spring.mapper

import boot.spring.entity.Message
import boot.spring.entity.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface MessageMapper {
    fun getHistoryMessage(@Param("userId") userId: Int, @Param("friendId") friendId: Int): List<Message>
    fun saveMessage(message: Message)
    fun getMainMessage(userId: Int): List<Message>?
    fun selectUserById(userId: Int):User
}