package boot.spring

import boot.spring.config.Slf4j
import boot.spring.config.Slf4j.Companion.log
import boot.spring.entity.User
import com.alibaba.fastjson.JSON
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import kotlin.test.assertContains


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
@Slf4j
open class ChatControllerTest {

    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Before
    fun setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        log.debug(mvc.toString())
    }

    @Test
    open fun register() {
        log.debug("register")
        val uri = "http://localhost:8080/api/register"
        val user = User("lixin2", "123456")
        val inputJson = JSON.toJSONString(user)
        println("inputJson: $inputJson")
        val builder = MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(inputJson)
        println("mvc:$mvc")
        val perform = mvc.perform(builder)
        val mvcResult = perform.andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        println(content)
        val TResult = JSON.parseObject(content, boot.spring.entity.TResult::class.java)
        //断言状态码，通过断言才会通过测试
        assertContains(listOf(200,400), TResult.code)
    }
}