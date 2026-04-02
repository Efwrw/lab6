import application.CommandInvoker
import java.util.concurrent.LinkedBlockingQueue

class ServerContainer {
    val requestsBuffer = LinkedBlockingQueue<RpcRequest>()
    val dispatcher: Dispatcher = Dispatcher(this)
    val commandInvoker: CommandInvoker = CommandInvoker(this)
    val listener: Connector = Connector(this)

    fun up(){
        listener.run()
        while(true){
            if(requestsBuffer.isNotEmpty()){
                TODO("добавить логику диспатча")
            }
        }
    }
}