package commands

import ServerContainer
import application.buildOrganization
import domain.Organization

class Update (
    override val container: ServerContainer
): Command {
    override val name = "update"
    override val description = "Обновляет элемент в коллекции по заданному id"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager

        val id: Int
        try {
            id = args[0].toInt()
        }
        catch (e: Throwable) {
            throw IllegalArgumentException("Неверный формат аргумента.")
        }
        if (!collectionManager.checkID(id)) {
            val org: Organization = buildOrganization(collectionManager, data)
            collectionManager.updateById(id, org)
            return "Организация успешно обновлена."
        }
        else throw IllegalArgumentException("Указанный ID ($id) не существует. Воспользуйтесь командой 'add' для добавления новой организации")
    }
}