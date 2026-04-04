class Dispatcher(
    val container: ServerContainer,
) {
    val invoker = container.commandInvoker

    fun handleRequest(rpcRequest: RpcRequest): RpcResponse {
        try {
            val result = invoker.handleInput(rpcRequest)
            return RpcResponse(ApiCodes.SUCCESS, result)
        } catch (e: Exception){
            val rpc = RpcResponse(ApiCodes.ERROR, e.message ?: "No error message specified")

            return rpc
        }
    }
}