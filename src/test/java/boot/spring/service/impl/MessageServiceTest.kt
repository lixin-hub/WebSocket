package boot.spring.service.impl

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner::class)
internal class MessageServiceTest {
@Autowired
lateinit var messageService: MessageService
    @Test
    fun historyMessage() {
        val historyMessage = messageService.historyMessage(1, 2)
        historyMessage.forEach(::println)
    }

    @Test
    fun getMainMessage() {
        val mainMessage = messageService.getMainMessage(1)
       mainMessage?.forEach {
           println(it)
       }
    }
}