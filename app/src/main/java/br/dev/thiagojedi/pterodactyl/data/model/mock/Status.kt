package br.dev.thiagojedi.pterodactyl.data.model.mock

import br.dev.thiagojedi.pterodactyl.data.model.CustomEmoji
import br.dev.thiagojedi.pterodactyl.data.model.Status
import java.text.SimpleDateFormat
import java.util.*

val formatWithoutZone = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
var SimpleStatus = Status(
    id = "109648375748440347",
    createdAt = formatWithoutZone.parse("2023-01-07T14:23:56.618Z") as Date,
    inReplyToId = null,
    inReplyToAccountId = null,
    sensitive = false,
    spoilerText = "",
    visibility = "public",
    language = "pt",
    uri = "https://cuscuz.in/users/jedi/statuses/109648375748440347",
    url = "https://cuscuz.in/@jedi/109648375748440347",
    repliesCount = 0,
    reblogsCount = 0,
    favouritesCount = 1,
    editedAt = formatWithoutZone.parse("2023-01-07T20:42:39.209Z"),
//local_only= null,
    favourited = false,
    reblogged = false,
    muted = false,
    bookmarked = false,
//pinned= false,
    content = "<p>Eu queria ser John Lennon um minuto só <br />Pra ficar no toca discos e você me ouvir</p><p>Eu queria ser aquele espelho do seu quarto<br />Nele você sempre olha antes de dormir...</p><p>🎼🎵🎸</p>",
//filtered= emptyList(),
//reblog= null,
    application = Status.Application(
        name = "Tusky",
        website = "https://tusky.app"
    ),
    account = Account,
    mediaAttachments = emptyList(),
    mentions = emptyList(),
    tags = emptyList(),
    emojis = emptyList(),
//card=null,
//poll=null
)
val ReplyStatus = Status(
    id = "109648610954104808",
    createdAt = formatWithoutZone.parse("2023-01-07T15:23:42.000Z"),
    inReplyToId = "109648608887939232",
    inReplyToAccountId = "109302720308687498",
    sensitive = false,
    spoilerText = "",
    visibility = "public",
    language = "pt",
    uri = "https://bolha.one/users/cadusilva/statuses/109648610782005146",
    url = "https://bolha.one/@cadusilva/109648610782005146",
    repliesCount = 0,
    reblogsCount = 0,
    favouritesCount = 0,
    editedAt = formatWithoutZone.parse("2023-01-07T15:23:55.000Z"),
    favourited = false,
    reblogged = false,
    muted = false,
    bookmarked = false,
    content = "<p><span class=\"h-card\"><a href=\"https://cuscuz.in/@jedi\" class=\"u-url mention\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">@<span>jedi</span></a></span> <span class=\"h-card\"><a href=\"https://ursal.zone/@Ajoyceda\" class=\"u-url mention\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">@<span>Ajoyceda</span></a></span> seria uma pena se a coxinha em questão fosse feita de massa de batata ou macaxeira, não massa de ossos 😆</p>",
//    filtered=[],
//    reblog=null,
    account = Account,
    mediaAttachments = emptyList(),
    mentions = listOf(
        Status.Mention(
            id = "109302720308687498",
            username = "jedi",
            url = "https://cuscuz.in/@jedi",
            acct = "jedi"
        ),
        Status.Mention(
            id = "109314333189122870",
            username = "Ajoyceda",
            url = "https://ursal.zone/@Ajoyceda",
            acct = "Ajoyceda@ursal.zone"
        )
    ),
    tags = emptyList(),
    emojis = emptyList(),
//    card = null,
//    poll = null
)
val StatusWithLinkAndHashtags = Status(
    id = "109620134468672525",
    createdAt = formatWithoutZone.parse("2023-01-02T14:41:48.867Z"),
    inReplyToId = null,
    inReplyToAccountId = null,
    sensitive = false,
    spoilerText = "",
    visibility = "public",
    language = "pt",
    uri = "https://cuscuz.in/users/jedi/statuses/109620134468672525",
    url = "https://cuscuz.in/@jedi/109620134468672525",
    repliesCount = 4,
    reblogsCount = 55,
    favouritesCount = 25,
    editedAt = null,
//        localOnly=false,
    favourited = false,
    reblogged = true,
    muted = false,
    bookmarked = false,
    pinned = false,
    content = "<p><a href=\"https://cuscuz.in/tags/FediVagas\" class=\"mention hashtag\" rel=\"tag\">#<span>FediVagas</span></a> :boost_rainbow: </p><p>Tá procurando emprego e não se importa de trabalhar na minha equipe e ter que aguentar minhas piadas de tiozão? Chegou a hora!</p><p>Se não, dá aquele boost maroto para ajudar, por favorzin?</p><p>Vaga para <a href=\"https://cuscuz.in/tags/WebDev\" class=\"mention hashtag\" rel=\"tag\">#<span>WebDev</span></a> backend <a href=\"https://cuscuz.in/tags/CSharp\" class=\"mention hashtag\" rel=\"tag\">#<span>CSharp</span></a> Pleno.<br />Trabalho regime <a href=\"https://cuscuz.in/tags/CLT\" class=\"mention hashtag\" rel=\"tag\">#<span>CLT</span></a>, remoto.<br /> <a href=\"https://agilecontent.gupy.io/job/eyJzb3VyY2UiOiJndXB5X3B1YmxpY19wYWdlIiwiam9iSWQiOjMwNzk5ODB9\" target=\"_blank\" rel=\"nofollow noopener noreferrer\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">agilecontent.gupy.io/job/eyJzb</span><span class=\"invisible\">3VyY2UiOiJndXB5X3B1YmxpY19wYWdlIiwiam9iSWQiOjMwNzk5ODB9</span></a></p>",
//        filtered=[],
//        reblog=null,
    application = Status.Application(
        name = "Web",
        website = null
    ),
    account = Account,
    mediaAttachments = emptyList(),
    mentions = emptyList(),
    tags = listOf(
        Status.Tag(
            name = "fedivagas",
            url = "https://cuscuz.in/tags/fedivagas"
        ),
        Status.Tag(
            name = "webdev",
            url = "https://cuscuz.in/tags/webdev"
        ),
        Status.Tag(
            name = "csharp",
            url = "https://cuscuz.in/tags/csharp"
        ),
        Status.Tag(
            name = "clt",
            url = "https://cuscuz.in/tags/clt"
        )
    ),
    emojis = listOf(
        CustomEmoji(
            shortcode = "boost_rainbow",
            url = "https://cuscuz.in/system/custom_emojis/images/000/002/795/original/5c24878751b2f0db.png",
            staticUrl = "https://cuscuz.in/system/custom_emojis/images/000/002/795/static/5c24878751b2f0db.png",
            visibleInPicker = true
        )
    ),
    card = Status.Card(
        url = "http://agilecontent.gupy.io/job/eyJqb2JJZCI6MzA3OTk4MCwic291cmNlIjoiZ3VweV9wdWJsaWNfcGFnZSJ9?jobBoardSource=gupy_public_page",
        title = "Pessoa Desenvolvedora Pl .NET (C#)",
        description = "O que esperamos das pessoas que se candidatam?Estamos interessados em pessoas desenvolvedoras que sejam indivíduos atenciosos, responsáveis e apaixonados, que apreciem desafios",
        type = "link",
        author_name = "",
        author_url = "",
        provider_name = "Pessoa Desenvolvedora Pl .NET (C#)",
        provider_url = "",
        html = "",
        width = 400,
        height = 210,
        image = "https://cuscuz.in/system/cache/preview_cards/images/000/080/551/original/75ef83c7975051a6.png",
        embed_url = "",
        blurhash = "U66aYT33%J%K^66L$%xGR#XltQoxAB-CNtNa"
    ),
//        poll=null
)