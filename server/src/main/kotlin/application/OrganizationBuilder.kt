package application

import application.exceptions.WrongArgumentException
import domain.Address
import domain.Coordinates
import domain.Organization
import domain.OrganizationType
import java.time.LocalDate

fun buildOrganization(cm: CollectionManager, data: Map<String, String>): Organization {
    val name = data["name"]?.trim()
    val x = data["x"]?.trim()?.toFloat()
    val y = data["y"]?.trim()?.toFloat()
    val turnover = data["turnover"]?.trim()?.toFloat()
    val fullName = data["fullName"]?.trim()
    val empCount = data["empCount"]?.trim()?.toLongOrNull()
    val street = data["street"]?.trim()
    val zip = data["zip"]?.trim()
    val type = data["type"]?.trim()?.lowercase()
    requireNotNull(name) {"Name is required"}
    requireNotNull(turnover) {"Turnover is required"}
    requireNotNull(fullName) {"Full name is required"}
    requireNotNull(type) {"Type is required"}
    requireNotNull(x) {"X coordinate is required"}
    requireNotNull(y){"Y coordinate is required"}
    val orgType = when (type) {
        "commercial" -> OrganizationType.COMMERCIAL
        "public" -> OrganizationType.PUBLIC
        "government" -> OrganizationType.GOVERNMENT
        "private limited company" -> OrganizationType.PRIVATE_LIMITED_COMPANY
        "open joint stock company" -> OrganizationType.OPEN_JOINT_STOCK_COMPANY
        else -> {
            throw WrongArgumentException("Введён некоректный формат типа организации")
        }
    }
    val addr = Address(street ?: "", zip ?: "")
    val coordinates = Coordinates(x, y)
    val id = cm.generateNewID()
    return Organization(id, name, coordinates, LocalDate.now(), turnover, fullName, empCount,orgType, addr)

}
