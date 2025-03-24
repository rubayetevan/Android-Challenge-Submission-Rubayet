package jp.speakbuddy.edisonandroidexercise.presentation.fact.models

data class FactUiState(
    val fact: String? = null,
    val factLength: Int = 0,
    val isLoading: Boolean = false
) {

    val showFactLength: Boolean = factLength > 100
    val isMultipleCats: Boolean = hasCats(fact)

    private fun hasCats(fact: String?): Boolean {
        fact ?: return false
        val catsCount = fact.lowercase().split(" ").count { it == "cats" }
        return catsCount > 0
    }
}
