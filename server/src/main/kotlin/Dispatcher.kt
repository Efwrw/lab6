class Dispatcher(
    val container: ServerContainer,
) {
    val invoker = container.commandInvoker
    val listener = container.listener

    fun handleRequest(contextRequest: ContextRequest): RpcResponse {
        try {
            val result = invoker.handleInput(contextRequest.request)
            return RpcResponse(ApiCodes.SUCCESS, result)
        } catch (e: Exception){
            val rpc = RpcResponse(ApiCodes.ERROR, e.message ?: "No error message specified")

            return rpc
        }
    }
}