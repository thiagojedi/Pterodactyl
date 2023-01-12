# Pterodactyl

> **Warning:** **THIS IS A WORK IN PROGRESS.** It does not have all the
> features. Heck, it doesn't have user data security yet. Use it at your own
> risk.

Pterodactyl is a client app for Mastodon[^mastodonapi] on android. Heavly
inspired by [Mastodon-android][masto01], and its forks [Megalodon][megs] and
[Moshidon][moshi], the main goal of this project is to have a Material You
(a.k.a. [Material 3][md3]) design.

[^mastodonapi]: And Mastodon API compliant services, like Pleroma.

## Why not a fork? Why not to contribute?

I've contributed to cited projects. And will continue to do so in the future.
There are a few reasons I created this project as a separate one:

1. I wanted some features that I could not add to those project because,
1. Those projects are in Java, and
1. I wanted a reason to learn Jetpack Compose, that requires Kotlin.

As the upstream developers were very clear they [do not want Kotlin][masto02] on
their codebase, I'm creating my own.

## Planned features

### Beta

- [ ] Timelines
  - [ ] Federated
  - [ ] Local
  - [ ] Home
- [ ] Statuses
  - [ ] Interaction (fav, boost)
  - [ ] Context (replies, etc)
  - [ ] Media
- [ ] Login
- [ ] User profile
  - [ ] User Statuses
  - [ ] User actions (follow, silence, etc)
- [ ] Compose statuses
  - [ ] Edit
  - [ ] Custom Emoji selector
  - [ ] Mention selector

### Release 1.0

- [ ] Search
- [ ] Discovery page
  - [ ] Hashtags
  - [ ] Posts
  - [ ] News
  - [ ] For you
- [ ] Notifications
- [ ] Filters

### Planned

- [ ] Push Notifications
- [ ] Tablet layout
- [ ] Themes

## Will you publish it to the Play Store, F-Droid or other store?

Let's wait until the app is ready, shall we?


> "Alpha, Rita's escaped! Recruit a team of teenagers with attitude!"
>
> \- Zordon, in _Mighty Morphing Power Rangers_

[masto01]: https://github.com/mastodon/mastodon-android
[masto02]: https://github.com/mastodon/mastodon-android/issues/3
[megs]: https://github.com/sk22/megalodon
[moshi]: https://github.com/LucasGGamerM/moshidon
[md3]: https://m3.material.io
