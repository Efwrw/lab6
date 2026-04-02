import application.CommandInvoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class ServerContainer {
    val requestsBuffer = LinkedBlockingQueue<RpcRequest>()
    val dispatcher: Dispatcher = Dispatcher(this)
    val commandInvoker: CommandInvoker = CommandInvoker(this)
    val listener: Connector = Connector(this)

    fun up(){
        thread(true) {listener.run()}
        while(true){
            if(requestsBuffer.isNotEmpty()){
                TODO("реализовать логику диспетчера")
            }
        }
    }
}