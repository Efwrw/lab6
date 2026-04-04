class Parser(container: ClientContainer) {
    val commands: Map<String, CommandDTO> = mapOf()
    val commandsNames = commands.keys.toList()
    val io = container.IO

    fun parse(): RpcRequest?{
        val mainLine = io.readLine()

        val tokens = mainLine?.split(" ")
        if (commandsNames.contains(tokens!![0])){
            return null
        }

        val currCommand = commands[tokens[0]]

        if (tokens.size - 1 != currCommand?.argCount){
            return null
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