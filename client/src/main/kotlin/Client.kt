import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.util.Scanner

class Client {
    fun run(){
        try {
            println("enter the message")
            val clientSocket = Socket("127.0.0.1", 5433)
            val sc = Scanner(System.`in`)
            val reader = InputStreamReader(clientSocket.getInputStream())
            val writer = OutputStreamWriter(clientSocket.getOutputStream())
            val buffReader = BufferedReader(reader)

            val rpcRequest = RpcRequest(
                name = sc.nextLine(),
                data = HashMap()
            )

            val rpcRequestJSON = Json.encodeToString(rpcRequest)

            println(rpcRequestJSON)
            writer.write(rpcRequestJSON + "\n")
            writer.flush()
            println(buffReader.readLine())
        } catch (e: Exception) {
            TODO("add client disconnect logic")
        }


    }
}