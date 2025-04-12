data class GameSettings(
    val ageGroup: String,       // "Для взрослых" или "С детьми"
    val playerCount: Int,       // 4-16
    val useCharacterCards: Boolean,
    val useHobbyCards: Boolean
)