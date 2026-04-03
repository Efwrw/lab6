class ViewResolver {
    fun resolve(rpcResponse: RpcResponse): String{
        val view = when(rpcResponse.code){
            ApiCodes.SUCCESS -> "успешно: " + rpcResponse.data
            ApiCodes.ERROR -> "ошибка: " + rpcResponse.data
            ApiCodes.INFO -> rpcResponse.data
        }

        return view
    }
}