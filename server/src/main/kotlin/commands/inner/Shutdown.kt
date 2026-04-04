package commands.inner

import ServerContainer

class Shutdown(serverContainer: ServerContainer): InnerCommand {
    override val name = "shutdown"
    override fun execute(){
        throw ExitSignal()
    }
}