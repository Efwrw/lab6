data class CommandDTO(
    val name: String,
    val argCount: Int,
    val requiredFields: List<String>
)