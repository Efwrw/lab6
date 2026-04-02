package commands

import ServerContainer

interface Command {
    val container: ServerContainer
    val name: String
    val description: String
    fun execute(args: List<String>, data: Map<String, String>): String
}
