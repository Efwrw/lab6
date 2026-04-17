class Dispatcher(
    val container: ServerContainer,
) {
    val invoker = container.commandInvoker

    fun handleRequest(request: Request): Response {
       when(request){
           is Request.ExecuteCommand ->  try {
               val result = invoker.handleInput(request)
               return result
           } catch (_: ExitSignal){
               return Response.Shutdown
           } catch (e: Exception){
               val rpc = Response.Error(e.message ?: "No error message specified")

               return rpc
           }
           is Request.HandShake -> try {
               val response = Response.HandShake(invoker.getCommands())
               return response
           }
           catch (e: Exception){
               println(e.message ?: "No error message specified")
           }
       }
        return Response.Error("Something went wrong")
    }
}