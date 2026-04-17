package commands

import ClientContainer

class ExecuteScript: Command {
    override val name: String = "execute_script"
    override val description: String = "выполняет указанный скрипт"
    override fun execute(context: ClientContainer) {
    }
}