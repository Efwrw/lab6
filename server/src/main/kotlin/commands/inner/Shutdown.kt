package commands.inner

import ExitSignal
import ServerContainer

class Shutdown (
): InnerCommand {
    override val name = "shutdown"
    override val description = "Завершает процесс сервера"

    override fun execute(context: ServerContainer): Nothing{
        throw ExitSignal()
    }
}