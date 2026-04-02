package commands

import ServerContainer

class SumOfEmployeesCount(
    override val container: ServerContainer,
): Command {
    override val name = "sum_of_employees_count"
    override val description = "Возвращает количество работяг во всей коллекции"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager
        val count = collectionManager.sumEmployees()

        return "Общее количество работяг в коллекции: $count"
    }
}