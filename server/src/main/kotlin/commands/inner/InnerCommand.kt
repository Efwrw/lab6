package commands.inner

interface InnerCommand {
    val name: String
    fun execute()
}