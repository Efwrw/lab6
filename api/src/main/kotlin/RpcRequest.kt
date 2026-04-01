import kotlinx.serialization.Serializable

@Serializable
data class RpcRequest(
    val name: String,
    val data: Map<String, String>
)