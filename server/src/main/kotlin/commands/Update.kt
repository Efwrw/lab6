package commands

import Response
import ServerContainer
import application.buildOrganization
import domain.Organization

class Update (
): Command {
    override val name = "update"
    override val args = listOf("ID", "Name", "X", "Y", "Annual turnover", "Full name (unique)", "Employee count", "Street", "Zip code", "Type")
    override val description = "Обновляет элемент в коллекции по заданному id"

    override fun execute(context: ServerContainer, args: List<String>): Response {
        val collectionManager = context.collectionManager

        val id: Int
        try {
            id = args[0].toInt()
        }
        catch (_: Throwable) {
            return Response.Error("Неверный формат аргумента.")
        }
        if (!collectionManager.checkID(id)) {
            val preparedArgs = args.drop(1)
            val org: Organization = buildOrganization(collectionManager, preparedArgs)
            collectionManager.updateById(id, org)
            return Response.Info("Организация успешно обновлена.")
        }
        else return Response.Info("Указанный ID ($id) не существует. Воспользуйтесь командой 'add' для добавления новой организации")
    }
}