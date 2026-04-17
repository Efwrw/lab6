package commands

import Response
import ServerContainer
import application.buildOrganization
import domain.Organization

class RemoveGreater (
): Command {
    override val name = "remove_greater"
    override val args = listOf("Name", "X", "Y", "Annual turnover", "Full name (unique)", "Employee count", "Street", "Zip code", "Type")
    override val description = "Удаляет из коллекции все элементы, превышающие заданный"

    override fun execute(context: ServerContainer, args: List<String>): Response {
        val collectionManager = context.collectionManager

        val org: Organization = buildOrganization(collectionManager, args)
        val count = collectionManager.countGreater(org)

        collectionManager.removeGreater(org)
        return Response.Info("Из коллекции удалено $count элементов")
    }
}