import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

class ClientContainer {
    val resolver = ViewResolver()
    val IO: IOPort = CliManager()
    val parser = Parser(this)
    val clientEnt = Client(this)
    var socket: SocketChannel? = null
    lateinit var channelIO: ChannelIO




    fun up(){
        val address = InetSocketAddress("127.0.0.1", 5432)
        try {
            val client = SocketChannel.open(address)
            client.configureBlocking(true)
            this.socket = client
            this.channelIO = ChannelIO(client)
            println(channelIO.toString())
            while (true) {
                clientEnt.run()
            }
        } catch (_: ExitSignal){
            return
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }
}