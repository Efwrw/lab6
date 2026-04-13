package commands

import ServerContainer

class Help (
    override val container: ServerContainer,
): Command {
    override val name = "help"
    override val description = "Выводит информацию о всех доступных командах"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val commandCollection = container.commandInvoker.getCommands()
        val description = StringBuilder()
        commandCollection.forEach { description.append("${it.name} - ${it.description}\n") }
        return description.toString()
    }
}