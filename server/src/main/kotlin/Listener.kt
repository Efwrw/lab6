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


        while (true) {
            val clientSocket = serverSocket.accept()
            println("Client connected: ${clientSocket.inetAddress.hostAddress}")
            val clientThread = thread(true) {

                    val reader = InputStreamReader(clientSocket.getInputStream())
                    val buffReader = BufferedReader(reader)
                    while (clientSocket.isConnected) {
                        if (buffReader.ready()) {
                            val rpcRequestJSON = buffReader.readLine()
                            println(rpcRequestJSON)
                            val rpcRequestObject = Json.decodeFromString<RpcRequest>(rpcRequestJSON)
                            val contextRequest = ContextRequest(clientSocket, rpcRequestObject)
                            serverContainer.requestsBuffer.put(contextRequest)
                        }
                    }
                }
            }
        }


//    private fun handleClient(clientSocket: Socket){
//        try {
//            val reader = InputStreamReader(clientSocket.getInputStream())
//            val buffReader = BufferedReader(reader)
//
//            println(rpcRequestJSON)
//
//        }
//        catch (e: Exception){
//            println(e.message)
//        }
//
//    }
    fun writeClient(clientSocket: Socket, response: RpcResponse){
        run{
            val writer = OutputStreamWriter(clientSocket.getOutputStream())
            val responseJSON = Json.encodeToString(response)
            writer.write(responseJSON + "\n")
            writer.flush()
            println("Message sent: $responseJSON")
        }
    }
}