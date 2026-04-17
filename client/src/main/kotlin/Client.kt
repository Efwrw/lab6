class Client(val clientContainer: ClientContainer) {
    val io = clientContainer.IO
    val resolver = clientContainer.resolver
    fun run() {
        try {
            val channelIO = clientContainer.channelIO
            io.printBefore("> ")
            val input = io.readLine()
            input?.let{
                val request = clientContainer.invoker.resolveCommand(input.trim()) ?: return
                channelIO.write(request)
            }
            val responseFromJson = channelIO.read() ?: return
            resolver.resolve(responseFromJson)
        } catch (e: ScriptError) {
            io.printLine("ошибка выполнения скрипта" + e.message)
        } catch (e: IllegalArgumentException){
            io.printLine(e.message)
        }
    }
}