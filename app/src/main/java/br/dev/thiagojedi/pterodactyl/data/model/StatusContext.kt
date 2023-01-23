package br.dev.thiagojedi.pterodactyl.data.model

data class StatusContext(val ancestors: List<Status>, val descendants: List<Status>)
