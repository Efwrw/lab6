package commands

import ServerContainer
import application.Handler
import application.buildOrganization
import domain.Organization

class RemoveGreater (
    override val container: ServerContainer
): Command {
    override val name = "remove_greater"
    override val description = "Удаляет из коллекции все элементы, превышающие заданный"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager

        val org: Organization = buildOrganization(collectionManager, data)
        val count = collectionManager.countGreater(org)

        collectionManager.removeGreater(org)
        return "Из коллекции удалено $count элементов"
    }
}