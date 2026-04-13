import kotlinx.serialization.Serializable

@Serializable
sealed class Request {
    @Serializable
    data class ExecuteCommand(
        val commandName: String,
        val args: List<String>,

    ) : Request()
}