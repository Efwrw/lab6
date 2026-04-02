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
        try {
            val reader = InputStreamReader(clientSocket.getInputStream())
            val buffReader = BufferedReader(reader)
            val rpcRequestJSON = buffReader.readLine() ?: return
            println(rpcRequestJSON)
            val rpcRequestObject = Json.decodeFromString<RpcRequest>(rpcRequestJSON)
            val contextRequest = ContextRequest(clientSocket, rpcRequestObject)
            serverContainer.requestsBuffer.put(contextRequest)
        }
        catch (e: Exception){
            println(e.message)
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