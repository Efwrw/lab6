class Client(val clientContainer: ClientContainer) {
    val io = clientContainer.IO
    val resolver = clientContainer.resolver
    val parser = clientContainer.parser
    fun run() {
        try {
            val channelIO = clientContainer.channelIO
            io.printBefore("> ")
            val rpcRequest = parser.parse()


            channelIO!!.write(rpcRequest)
            val responseFromJson = channelIO.read() ?: return

            val resolvedResponse = resolver.resolve(responseFromJson)
            io.printLine(resolvedResponse)
        } catch (e: Exception) {
            io.printLine("ошибка: " + e.message)
            throw e
        }


    }
}