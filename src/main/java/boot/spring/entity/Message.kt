package boot.spring.entity

import com.alibaba.fastjson.annotation.JSONField
import java.util.*

/**
 * 消息实体
 */
data class Message(
    val id: Int=0,
    var from: Int = 0,
    var to: Int = 1,
    var content: String = "test",
    @JSONField(format = "yyyy-MM-dd")
    var date: Date? = Date(),
    @JSONField(format = "HH:mm:ss")
    var time: Date? = Date(),
    var path:String?=null,
    var fromUser: User?=null,
    var toUser: User?=null
    )