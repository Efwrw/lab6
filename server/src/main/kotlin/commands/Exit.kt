package commands

import ServerContainer

class Exit (
    override val container: ServerContainer
): Command{
    override val name = "exit"
    override val description = "Отключается от сервера."

    override fun execute(args: List<String>, data: Map<String, String>): String {

        throw Exception("Отбой парни")
    }
}