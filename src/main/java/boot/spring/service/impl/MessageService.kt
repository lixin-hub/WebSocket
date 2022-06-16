package boot.spring.service.impl

import boot.spring.entity.Message

import boot.spring.mapper.MessageMapper
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class MessageService {
    @Resource
    private lateinit var messageMapper: MessageMapper

    fun historyMessage(userId: Int, friendId: Int): List<Message> {
        return messageMapper.getHistoryMessage(userId, friendId)
    }

    fun getMainMessage(userId: Int): List<Message>? {
        return messageMapper.getMainMessage(userId)
    }
}