import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class Client(clientContainer: ClientContainer) {
    val io = clientContainer.IO
    val resolver = clientContainer.resolver
    val parser = clientContainer.parser
    fun run(clientSocket: Socket){
        try {
            io.printBefore("> ")
            val input = io.readLine()
            val arrayOfArgs = parser.parse(input ?: "")
            val first = arrayOfArgs[0]
            arrayOfArgs.drop(0)
            val reader = InputStreamReader(clientSocket.getInputStream())
            val writer = OutputStreamWriter(clientSocket.getOutputStream())
            val buffReader = BufferedReader(reader)

            val rpcRequest = RpcRequest(
                name = first,
                data = HashMap(),
                args = arrayOfArgs
            )

            val rpcRequestJSON = Json.encodeToString(rpcRequest)

            println(rpcRequestJSON)
            writer.write(rpcRequestJSON + "\n")
            writer.flush()

            val responseFromJson = Json.decodeFromString<RpcResponse>(buffReader.readLine())
            val resolvedResponse = resolver.resolve(responseFromJson)
            io.printLine(resolvedResponse)
        } catch (e: Exception) {
            println(e.printStackTrace())
        }


    }
}