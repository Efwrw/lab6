package commands

import ServerContainer
import application.buildOrganization
import domain.Organization

class Add(
    override val container: ServerContainer,
): Command {
    override val description: String = "Добавляет организацию в коллекцию"
    override val name: String = "add"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager

        val org: Organization = buildOrganization(collectionManager, data)

        collectionManager.add(org)
        return "Организация '${org.name}' успешно добавлена."
    }

}
