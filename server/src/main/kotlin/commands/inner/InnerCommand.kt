package commands.inner

import ServerContainer

interface InnerCommand {
    val name: String
    val description: String
    fun execute(context: ServerContainer, args: List<String>)
}