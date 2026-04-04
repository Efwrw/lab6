import jdk.internal.joptsimple.internal.Messages.message
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Client(val clientContainer: ClientContainer) {
    val io = clientContainer.IO
    val resolver = clientContainer.resolver
    val parser = clientContainer.parser
    fun run() {
        try {
            val channelIO = clientContainer.channelIO
            io.printBefore("> ")
            val input = io.readLine()
            val arrayOfArgs = parser.parse(input ?: "")
            val first = arrayOfArgs[0]
            arrayOfArgs.drop(0)


            val rpcRequest = RpcRequest(
                name = first,
                data = HashMap(),
                args = arrayOfArgs
            )

            channelIO!!.write(rpcRequest)
            val responseFromJson = channelIO.read() ?: return

            val resolvedResponse = resolver.resolve(responseFromJson)
            io.printLine(resolvedResponse)
        } catch (e: Exception) {
            println(e.printStackTrace())
        }


    }
}