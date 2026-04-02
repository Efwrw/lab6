import java.net.Socket

data class ContextRequest(
    val clientSocket: Socket,
    val request: RpcRequest,
)
