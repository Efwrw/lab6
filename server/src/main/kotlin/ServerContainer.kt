import application.CommandInvoker
import data.StorageManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.OutputStreamWriter
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class ServerContainer {
    val requestsBuffer = LinkedBlockingQueue<ContextRequest>()
    val dispatcher: Dispatcher = Dispatcher(this)
    val commandInvoker: CommandInvoker = CommandInvoker(this)
    val listener: Listener = Listener(this)
    val storageManager: StorageManager = StorageManager(this)
    val collectionManager = application.CollectionManager(storageManager.downloadCollection(""))

    fun up(){
        thread(true){listener.run()}

        while(true){
            if(requestsBuffer.isNotEmpty()){
                val contextRequest = requestsBuffer.poll()
                val result = dispatcher.handleRequest(contextRequest)
                listener.writeClient(contextRequest.clientSocket, result)
            }
        }
    }
}