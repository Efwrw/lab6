package commands

import ServerContainer
import domain.OrganizationType

class CountByType (
    override val container: ServerContainer
): Command {
    override val name = "count_by_type"
    override val description = "Подсчитывает количество организаций заданного типа"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val neatArgument = args[0].uppercase().trim().replace(" ", "_")
        val waitIsItTrue = OrganizationType.entries.any{ it.toString() == neatArgument }
        val collectionManager = container.collectionManager

        if (waitIsItTrue){
            val count = collectionManager.countType(OrganizationType.valueOf(neatArgument))

            return "Количество организаций такого типа: $count"
        }
        else{
            val neatOrganizationTypes = OrganizationType
                .entries
                .toString()
                .replace("_", " ")
            val n = neatOrganizationTypes.length

            throw IllegalArgumentException("Неправильный тип организации\nДоступные типы для ввода: ${neatOrganizationTypes.substring(1, n - 1)}")
        }

    }
}