class ViewResolver {
    fun resolve(response: Response): String{
        return when(response){
            is Response.Info -> response.message

            is Response.Error -> "ошибка: " + response.message

            is Response.Shutdown -> throw ExitSignal()
        }
    }
}