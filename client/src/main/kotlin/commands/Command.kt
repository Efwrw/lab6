package commands

import ClientContainer

interface Command {
    val name: String
    val description: String
    fun execute(context: ClientContainer)
}