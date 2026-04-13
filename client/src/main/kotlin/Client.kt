class Client(val clientContainer: ClientContainer) {
    val io = clientContainer.IO
    val resolver = clientContainer.resolver
    val parser = clientContainer.parser
    fun run() {
        val channelIO = clientContainer.channelIO
        io.printBefore("> ")
        val request = parser.parse()


        channelIO.write(request)
        val responseFromJson = channelIO.read() ?: return

        val resolvedResponse = resolver.resolve(responseFromJson)
        io.printLine(resolvedResponse)


    }
}