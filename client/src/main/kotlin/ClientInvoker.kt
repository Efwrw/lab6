import command.CommandSyntax
import commands.Command
import commands.Exit
import commands.Help

class ClientInvoker(
    private val context: ClientContainer,
) {
    val serverCommands = HashMap<String, CommandSyntax>()
    val clientCommands = HashMap<String, Command>()
    init {
        clientCommands["help"] = Help()
        clientCommands["exit"] = Exit()
    }
    fun resolveCommand(command: String): Request? {
        val clientCommand = clientCommands[command]
        val serverCommand = serverCommands[command]
        if (serverCommand != null) {
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
        clientCommand?.execute(context)
        return null
    }

    fun getSyntax(name: String): List<String> {
        val command = serverCommands[name] ?: throw IllegalArgumentException("Неизвестная комманда: $name")
        return command.args
    }

    fun load(source: Response.HandShake){
        val commandsSyntax = source.commands
        commandsSyntax.forEach {serverCommands[it.name] = it}
    }

    fun addCommand(command: Command){
        clientCommands[command.name] = command
    }

    fun clear(){
        serverCommands.clear()
    }
}