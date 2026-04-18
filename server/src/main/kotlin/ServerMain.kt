import commands.inner.ExitSignal

fun main(args: Array<String>) {
    var filePath: String = ""
    if (args.isNotEmpty()) {
        filePath = args[0]
    }
    val serverContainer = ServerContainer(filePath)
    try{serverContainer.up()}
    catch(e: ExitSignal){
        println("сервер выключается")
    }
}
