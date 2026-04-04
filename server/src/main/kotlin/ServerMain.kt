import commands.inner.ExitSignal

fun main() {
    val serverContainer = ServerContainer()
    try{serverContainer.up()}
    catch(e: ExitSignal){
        println("сервер выключается")
    }
}
