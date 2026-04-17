package commands

import ClientContainer
import ExitSignal
class Exit: Command {
    override val name: String = "exit"
    override val description: String = "Завершает процесс клиента"
    override fun execute(context: ClientContainer) {
        context.IO.printLine("Программа завершается...")
        throw ExitSignal()
    }
}