package br.dev.thiagojedi.pterodactyl.data.model.mock

import br.dev.thiagojedi.pterodactyl.data.model.Account
import java.text.SimpleDateFormat
import java.util.*

val Account = Account(
    id = "109302720308687498",
    username = "jedi",
    acct = "jedi",
    display_name = "Thiago Jedi",
    locked = false,
    bot = false,
    createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2022-11-07T00:00:00.000Z") as Date,
    note = "<p>Thiago, designated as &quot;Jedi&quot; by friends and colleagues. Lover of programing languages. Full snack dev (a.k.a. if it pays my food, I&#39;m in). Tech Leader. Follow me, I&#39;m lost too.</p><p>Pro tip: I post a lot, and mostly in portuguese, you may filter it out if you want.</p><p>Thiago, apelidado de &quot;Jedi&quot; pelos amigos e colegas. Amante de linguagens de programação. Desenvolvedor full snack (pagou o lanche, to dentro). Líder técnico. Me siga, estou perdido também.</p><p>Natalense, Potiguar, com orgulho.</p>",
    url = "https://cuscuz.in/@jedi",
    avatar = "https://cuscuz.in/system/accounts/avatars/109/302/720/308/687/498/original/9f99c3cf45228107.png",
    avatarStatic = "https://cuscuz.in/system/accounts/avatars/109/302/720/308/687/498/original/9f99c3cf45228107.png",
    header = "https://cuscuz.in/system/accounts/headers/109/302/720/308/687/498/original/a2d86f9adef03dae.gif",
    headerStatic = "https://cuscuz.in/system/accounts/headers/109/302/720/308/687/498/static/a2d86f9adef03dae.png",
    followersCount = 165,
    followingCount = 244,
    statusesCount = 1478,
    lastStatusAt = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-07") as Date,
    emojis = emptyList(),
    fields = listOf(
        br.dev.thiagojedi.pterodactyl.data.model.Account.Field(
            name = "Blog/portifólio",
            value = "<a href=\"https://thiagojedi.github.io\" target=\"_blank\" rel=\"nofollow noopener noreferrer me\"><span class=\"invisible\">https://</span><span class=\"\">thiagojedi.github.io</span><span class=\"invisible\"></span></a>",
            verifiedAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2022-11-09T18:08:40.836+00:00")
        ),
        br.dev.thiagojedi.pterodactyl.data.model.Account.Field(
            name = "working at",
            value = "<a href=\"https://www.agilecontent.com\" target=\"_blank\" rel=\"nofollow noopener noreferrer me\"><span class=\"invisible\">https://www.</span><span class=\"\">agilecontent.com</span><span class=\"invisible\"></span></a>",
            verifiedAt = null
        ),
        br.dev.thiagojedi.pterodactyl.data.model.Account.Field(
            name = "Idiomas/Languages", value = "PT-Br, PT-Pt, EN, ES,", verifiedAt = null
        ),
        br.dev.thiagojedi.pterodactyl.data.model.Account.Field(
            name = "Pronomes", value = "Ele/Dele He/His", verifiedAt = null
        )
    )
)