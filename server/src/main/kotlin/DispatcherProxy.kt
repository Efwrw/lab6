class DispatcherProxy(val dispatcher: Dispatcher) {
    fun handleRequest(rpcRequest: RpcRequest): RpcResponse {
        return try{
            dispatcher.handleRequest(rpcRequest)
        }catch (e: Exception){
            return RpcResponse(ApiCodes.ERROR, e.message ?: "no message specified")
        }
    }
}
