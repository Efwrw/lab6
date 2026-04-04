import application.CommandInvoker
import data.StorageManager
import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel

class ServerContainer {
    val commandInvoker = CommandInvoker(this)
  //  val requestsBuffer = LinkedBlockingQueue<ContextRequest>()
    val dispatcher: Dispatcher = Dispatcher(this)
    //val listener: Listener = Listener(this)
    val storageManager: StorageManager = StorageManager(this)
    val collectionManager = application.CollectionManager(storageManager.downloadCollection(""))

    fun up() {
        val selector = Selector.open()
        val serverSocket = ServerSocketChannel.open()

        serverSocket.bind(InetSocketAddress("127.0.0.1", 5432))
        serverSocket.configureBlocking(false)
        serverSocket.register(selector, SelectionKey.OP_ACCEPT)

        println("Сервер запущен на 127.0.0.1:5432")

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

                        val rpcRequest = io.read()
                        println(rpcRequest?.data)
                        if (rpcRequest != null) {
                            println("Получен запрос: $rpcRequest")
                            val rpcResponse = dispatcher.handleRequest(rpcRequest)
                            println(rpcResponse.data)
                            try {
                                io.write(rpcResponse)
                            } catch (e: Exception) {e.printStackTrace()}
                        }
                    } catch (e: Exception) {
                        println("Клиент отключился или произошла ошибка")
                        key.channel().close()
                        key.cancel()
                    }
                }
            }
        }
    }
}