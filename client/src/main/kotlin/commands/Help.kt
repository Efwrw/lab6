package commands

import ClientContainer

class Help: Command {
    override val name: String = "help"
    override val description: String = "Выводит справку о текущих командах."
    override fun execute(context: ClientContainer){
        val io = context.IO
        val invoker = context.invoker
        val strBuilder = StringBuilder()
        invoker.serverCommands.forEach { strBuilder.append("${it.key} - ${it.value.description}\n") }
        invoker.clientCommands.forEach { strBuilder.append("${it.key} - ${it.value.description}\n") }
        io.printLine(strBuilder.toString())
    }
}