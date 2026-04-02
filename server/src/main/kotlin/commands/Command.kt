package commands

import ServerContainer
import application.Handler

interface Command {
    val container: ServerContainer
    val name: String
    val description: String
    fun execute(argument: String, data: Map<String, String>): String
}
