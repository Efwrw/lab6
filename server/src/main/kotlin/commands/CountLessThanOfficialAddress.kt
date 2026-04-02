package commands

import ServerContainer
import domain.Address

class CountLessThanOfficialAddress (
    override val container: ServerContainer
): Command {
    override val name = "count_less_than_official_address"
    override val description = "Подсчитывает количество организаций чей адрес меньше заданного"

    override fun execute(args: List<String>, data: Map<String, String>): String {
        val collectionManager = container.collectionManager

        val street = data["street"] as String
        val zip = data["zip"] as String
        val address = Address(street, zip)

        val count = collectionManager.countLessAddress(address)

        return "Количество организаций с меньшим адресом - $count"
    }
}