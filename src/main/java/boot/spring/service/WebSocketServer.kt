package boot.spring.service

import boot.spring.entity.Constant
import boot.spring.entity.Message
import boot.spring.entity.TResult
import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@ServerEndpoint("/webSocket/{userId}")
@Component
open class WebSocketServer {
    @Autowired
    private var loginService: LoginService? = null

    companion object {
        //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
        val onlineNumber = AtomicInteger()

        //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
        @JvmStatic
        val sessionPools = ConcurrentHashMap<Int, Session>()
        fun addOnlineCount() {
            onlineNumber.incrementAndGet()
        }

        fun subOnlineCount() {
            onlineNumber.decrementAndGet()
        }
    }

    //发送消息
    @Throws(IOException::class)
    open fun sendMessage(session: Session, message: String) {
        synchronized(session) {
            println("发送数据：$message")
            session.basicRemote.sendText(message)
        }
    }

    //给指定用户发送信息
    private fun sendInfo(userId: Int, message: String): TResult {
        val session = sessionPools[userId]
        try {
            session?.let {
                sendMessage(it, message)
            }
        } catch (e: Exception) {
            TResult(200, "error")
            e.printStackTrace()
        }
        return TResult(200, "登录成功")
    }

    // 群发消息
    private fun broadcast(message: String) {
        for (session in sessionPools.values) {
            try {
                sendMessage(session, message)
            } catch (e: Exception) {
                e.printStackTrace()
                continue
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    open fun onOpen(session: Session, @PathParam(value = "userId") userId: Int) {
        sessionPools[userId] = session
        addOnlineCount()
        print(session.basicRemote)
        loginService?.let {
            val user = it.getUserById(userId)
            println(user)
        }
        // 广播上线消息
//        user?.let {
//            println(user.username + "加入webSocket！当前人数为" + onlineNumber)
//            broadcast(JSON.toJSONString(Message(to = Constant.MESSAGE_TYPE_GROUP, content = user.username), true))
//        }
    }

    //关闭连接时调用
    @OnClose
    open fun onClose(@PathParam(value = "userId") userId: Int) {
        sessionPools.remove(userId)
        subOnlineCount()
//        loginService!!.getUserById(userId)?.let {
//            println(it.username + "断开webSocket连接！当前人数为" + onlineNumber)
//            // 广播下线消息
//            val msg = Message(to = Constant.MESSAGE_TYPE_GROUP, content = it.username)
//            broadcast(JSON.toJSONString(msg, true))
//        }
    }

    //收到客户端信息后，根据接收人的username把消息推下去或者群发
    // to=-1群发消息
    @OnMessage
    @Throws(IOException::class)
    open fun onMessage(message: String) {
        println("server get$message")
        val msg = JSON.parseObject(message, Message::class.java)
        if (msg.to == Constant.MESSAGE_TYPE_GROUP) {
            broadcast(JSON.toJSONString(msg, true))
        } else {
            sendInfo(msg.to, JSON.toJSONString(msg, true))
        }
    }

    //错误时调用
    @OnError
    open fun onError(session: Session?, throwable: Throwable) {
        println("发生错误")
        throwable.printStackTrace()
    }
}