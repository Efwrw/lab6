import application.CommandInvoker
import data.StorageManager
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel

class ServerContainer {
    val commandInvoker = CommandInvoker(this)
    val dispatcher: Dispatcher = Dispatcher(this)
    val storageManager: StorageManager = StorageManager(this)
    val collectionManager = application.CollectionManager(storageManager.downloadCollection(""))
    val listeningPort: Int = 3306

    fun up() {
        val selector = Selector.open()
        val serverSocket = ServerSocketChannel.open()

        serverSocket.bind(InetSocketAddress("127.0.0.1", listeningPort))
        serverSocket.configureBlocking(false)
        serverSocket.register(selector, SelectionKey.OP_ACCEPT)

        println("Сервер запущен на 127.0.0.1:$listeningPort")

        while (true) {
            selector.select()
            val selectionIterator = selector.selectedKeys().iterator()
            println(selectionIterator)
            while (selectionIterator.hasNext()) {
                val key = selectionIterator.next()

                selectionIterator.remove()

                if (!key.isValid) continue

                if (key.isAcceptable) {
                    val client = serverSocket.accept()
                    client.configureBlocking(false)

                    val io = ServerChannelIO(client)
                    client.register(selector, SelectionKey.OP_READ, io)

                    println("Client connected: ${client.remoteAddress}")
                }

                if (key.isReadable) {

                    val io = key.attachment() as ServerChannelIO

                    try {

                        val Request = io.read()
//                        println(Request?.data)
                        if (Request != null) {
                            println("Получен запрос: $Request")
                            val Response = dispatcher.handleRequest(Request)
//                            println(Response.data)
                            try {
                                io.write(Response)
                            } catch (e: Exception) {e.printStackTrace()}
                        }
                    } catch (e: Exception) {
                        println("Клиент отключился или произошла ошибка")
                        println(e.printStackTrace())
                        key.channel().close()
                        key.cancel()
                    }
                }
            }
        }
    }
}