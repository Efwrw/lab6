package commands

import ServerContainer
import application.buildOrganization
import domain.Organization

class RemoveLower (
    override val container: ServerContainer
): Command {
    override val name = "remove_lower"
    override val description = "Удаляет из коллекции все элементы, меньше чем"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager

        val org: Organization = buildOrganization(collectionManager, data)
        val count = collectionManager.countLower(org)

        collectionManager.removeLower(org)
        return "Из коллекции удалено $count элементов"
    }
}