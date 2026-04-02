import application.CommandInvoker
import data.StorageManager
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class ServerContainer {
    val commandInvoker = CommandInvoker(this)
    val requestsBuffer = LinkedBlockingQueue<ContextRequest>()
    val dispatcher: Dispatcher = Dispatcher(this)
    val listener: Listener = Listener(this)
    val storageManager: StorageManager = StorageManager(this)
    val collectionManager = application.CollectionManager(storageManager.downloadCollection(""))

    fun up(){
        thread(true){listener.run()}

        while(true){
            if(requestsBuffer.isNotEmpty()){
                val contextRequest = requestsBuffer.poll()
                println(contextRequest)
                val result = dispatcher.handleRequest(contextRequest)
                listener.writeClient(contextRequest.clientSocket, result)
            }
        }
    }
}