import kotlinx.serialization.Serializable

@Serializable
class RpcResponse(
    val code: ApiCodes,
    val data: String
)