import kotlinx.serialization.Serializable

@Serializable
class RpcResponse(
    val code: ApiCodes,
    val data: Map<String, String>,
    val message: String?
)