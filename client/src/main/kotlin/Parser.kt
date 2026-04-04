class Parser(container: ClientContainer) {
    val commands: Map<String, CommandDTO> = mapOf(Pair("help", CommandDTO(
        "help",
        0,
        listOf()
    )))
    val commandsNames = commands.keys.toList()
    val io = container.IO

    fun parse(): RpcRequest?{
        val mainLine = io.readLine()

        val tokens = mainLine?.split(" ")
        if (!commandsNames.contains(tokens!![0])){
            throw IllegalArgumentException("нет такой команды" + commands + tokens[0])
        }

        val currCommand = commands[tokens[0]]

        if (tokens.size - 1 != currCommand?.argCount){
            throw IllegalArgumentException("неверное количество аргументов" + tokens.size + currCommand?.argCount + currCommand)
        }

        val mapOfArgs = mutableMapOf<String, String>()

        for(i in currCommand.requiredFields){
            io.printBefore("введите $i: ")
            val currArg = io.readLine()
            mapOfArgs[i] = currArg ?: ""
        }

        return RpcRequest(
            tokens[0],
            tokens.drop(0),
            mapOfArgs
        )

    }
}