package commands

import ServerContainer

class RemoveByID  (
    override val container: ServerContainer,
): Command {
    override val name = "remove_by_id"
    override val description = "Удаляет из коллекции элемент по ID"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager
        try {
            if (collectionManager.checkID(args[0].toInt())) {
                throw IllegalArgumentException("Элемента с таким ID не существует")
            } else {
                collectionManager.removeById(args[0].toInt())
                return "Элемент с ID ${args[0]} удален."
            }
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("Введенный аргумент не является числом.")
        }
    }
}