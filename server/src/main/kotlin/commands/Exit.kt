package commands

import ExitSignal
import ServerContainer

class Exit (
    override val container: ServerContainer,
): Command {
    override val name = "exit"
    override val description = "Выходит с клиента"

    override fun execute(args: List<String>, data: Map<String, String>): Nothing{
        throw ExitSignal()
    }
}