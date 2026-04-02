package commands

import RpcRequest
import ServerContainer
import application.Handler
import application.buildOrganization
import commands.Command
import domain.Organization

class Add(
    override val container: ServerContainer,
): Command {
    override val description: String = "Добавляет организацию в коллекцию"
    override val name: String = "add"

    override fun execute(argument: String, data: Map<String, String>): String {
        val collectionManager = container.collectionManager

        val org: Organization = buildOrganization(collectionManager, data)

        collectionManager.add(org)
        return "Организация '${org.name}' успешно добавлена."
    }

}
