import command.CommandSyntax

class ClientInvoker(
    private val context: ClientContainer,
) {
    val commands = HashMap<String, CommandSyntax>()

    fun resolveCommand(command: String): Request {
        val syntax = getSyntax(command)
        val args = mutableListOf<String>()
        for (string in syntax) {
            context.IO.printBefore("$string:")
            val arg = context.IO.readLine()
            if (arg != null) {
                args.add(arg)
            }
        }

        return Request.ExecuteCommand(command, args)

    }

    fun getSyntax(name: String): List<String> {
        println()
        val command = commands[name] ?: throw IllegalArgumentException("Неизвестная комманда: $name")
        return command.args
    }

    fun load(source: Response.HandShake){
        val commandsSyntax = source.commands
        commandsSyntax.forEach {commands[it.name] = it}
    }
    fun clear(){
        commands.clear()
    }
}