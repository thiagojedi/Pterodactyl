package br.dev.thiagojedi.pterodactyl.data.model

data class FilterResult(
    val filter: Filter,
    val keyword_matches: List<String>,
    val status_matches: List<String>
)
