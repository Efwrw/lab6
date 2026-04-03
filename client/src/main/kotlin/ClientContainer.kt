import java.net.Socket

class ClientContainer {
    val resolver = ViewResolver()
    val IO: IOPort = CliManager()
    val client = Client(this)
    val parser = Parser()


    fun up(){
        val clientSocket = Socket("127.0.0.1", 5433)
        while (true) {
            client.run(clientSocket)
        }
    }
}