import kotlinx.serialization.encodeToString
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import kotlinx.serialization.json.*
import java.net.Socket
import kotlin.concurrent.thread

class Listener(val serverContainer: ServerContainer) {
    fun run() {
        val serverSocket = ServerSocket(5433)
        println("client connected")

        while (true) {
            val clientSocket = serverSocket.accept()

            thread(true){
                handleClient(clientSocket)
            }
        }
    }

    private fun handleClient(clientSocket: Socket){
        clientSocket.use{
            val reader = InputStreamReader(clientSocket.getInputStream())
            val buffReader = BufferedReader(reader)
            val writer = OutputStreamWriter(clientSocket.getOutputStream())
            val rpcRequestJSON = buffReader.readLine() ?: return
            println(rpcRequestJSON)
            val rpcRequestObject = Json.decodeFromString<RpcRequest>(rpcRequestJSON)
            val contextRequest = ContextRequest(clientSocket, rpcRequestObject)
            serverContainer.requestsBuffer.put(contextRequest)

            writer.write(rpcRequestObject.name + "\n")
            writer.flush()
            TODO("добавить логику обработки отключения клиента")
        }
    }
    fun writeClient(clientSocket: Socket, response: RpcResponse){
        clientSocket.use{
            val writer = OutputStreamWriter(clientSocket.getOutputStream())
            val responseJSON = Json.encodeToString(response)
            writer.write(responseJSON)
            writer.flush()
        }
    }
}