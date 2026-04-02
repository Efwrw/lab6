package commands

import ServerContainer

class Info (
    override val container: ServerContainer,
): Command {
    override val name = "info"
    override val description = "Выводит информацию о коллекции"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager
        val collection = collectionManager.getCollection()

        if(collection.isEmpty()) {
            return "Коллекция пуста :("
        }
        else {
            val strBuilder = StringBuilder()
            strBuilder.append("Количество элементов в коллекции: ${collection.size}\n")
            strBuilder.append("дата создания шедевра - ${collectionManager.getInitializationDate()}\n")

            val collect = collectionManager.getCollection()

            strBuilder.append("Организации в коллекции:\n")

            collect.forEach {strBuilder.append("${it.fullName} с id номер ${it.id}\n")} //небольшой попутный рефакторинг, нужно отвыкать от джава
            return strBuilder.toString()
        }

    }
}