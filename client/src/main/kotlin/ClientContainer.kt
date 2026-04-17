import java.io.IOException
import java.net.ConnectException
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

class ClientContainer {
    val resolver = ViewResolver(this)
    val IO: IOPort = CliManager()
    val parser = Parser(this)
    val clientEnt = Client(this)
    var socket: SocketChannel? = null
    val invoker: ClientInvoker = ClientInvoker(this)
    lateinit var channelIO: ChannelIO
    val serverPort: Int = 3306
    var timeout: Long = 5000

    fun up(){
        val address = InetSocketAddress("127.0.0.1", serverPort)
        try {
            val client = SocketChannel.open(address)
            client.configureBlocking(true)
            socket = client
            channelIO = ChannelIO(client)
            channelIO.write(Request.HandShake(null))
            val handshakeRespone = channelIO.read() ?: return up()
            resolver.resolve(handshakeRespone)
            timeout = 5000
            while (true) {
                clientEnt.run()
            }
        } catch (_: ExitSignal){
            return
        } catch (_: ConnectException){
            IO.printLine("не удалось подключится к серверу")
            Thread.sleep(timeout)
            if (timeout < 50000) timeout += 1000
            return up()
        }
        catch (_: IOException){
            IO.printLine("сервер разорвал подключение")
            return up()
        }
    }
}