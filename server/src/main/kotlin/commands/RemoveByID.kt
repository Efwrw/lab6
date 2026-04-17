package commands

import Response
import ServerContainer

class RemoveByID  (
): Command {
    override val name = "remove_by_id"
    override val args = listOf("ID")
    override val description = "Удаляет из коллекции элемент по ID"

    override fun execute(context: ServerContainer, args: List<String>): Response {
        val collectionManager = context.collectionManager
        try {
            if (collectionManager.checkID(args[0].toInt())) {
                return Response.Info("Элемента с таким ID не существует")
            } else {
                collectionManager.removeById(args[0].toInt())
                return Response.Info("Элемент с ID ${args[0]} удален.")
            }
        } catch (_: NumberFormatException) {
            throw IllegalArgumentException("Введенный аргумент не является числом.")
        }
    }
}