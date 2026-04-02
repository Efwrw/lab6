package commands

import ServerContainer

class Show(
   override val container: ServerContainer,
): Command {
    override val description: String = "Выводит список всех организаций"
    override val name: String = "show"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager
        if (collectionManager.getCollection().isEmpty()) return "Вы еще не успели насоздавать шедевров..."
        val strBuilder = StringBuilder()
        collectionManager.getCollection().forEach { strBuilder.append(it) }
        return strBuilder.toString()
    }
}
