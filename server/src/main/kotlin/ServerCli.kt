class ServerCli(
    val context: ServerContainer
) {
    fun processInput(): String?{
        if (System.`in`.available() > 0) {
            val input = readlnOrNull()
            return input
        }
        return null
    }
    fun write(message: String){
        println(message)
    }
}