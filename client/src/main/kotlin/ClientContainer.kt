import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class ClientContainer {
    val parser = Parser()
    val resolver = ViewResolver()
    val IO: IOPort = CliManager()
    val client = Client(this)



    fun up(){
        val clientSocket = Socket("127.0.0.1", 5433)
        val reader = InputStreamReader(clientSocket.getInputStream()).buffered()
        val writer = OutputStreamWriter(clientSocket.getOutputStream()).buffered()
        while (true) {
            client.run(reader, writer)
        }
    }
}