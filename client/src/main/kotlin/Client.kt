class Client(val clientContainer: ClientContainer) {
    val io = clientContainer.IO
    val resolver = clientContainer.resolver
    val parser = clientContainer.parser
    fun run() {
            val channelIO = clientContainer.channelIO
            io.printBefore("> ")
            val input = io.readLine()
            if (input != null) {
                val request = clientContainer.invoker.resolveCommand(input.trim()) ?: return
                channelIO.write(request)
            }
            val responseFromJson = channelIO.read() ?: return
            resolver.resolve(responseFromJson)
    }
}