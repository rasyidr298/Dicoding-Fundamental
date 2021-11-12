package id.rrdevfundamental.utils

import id.rrdevfundamental.data.db.entitiy.UserEntity
import id.rrdevfundamental.data.network.response.DetailResponse
import id.rrdevfundamental.data.network.response.User
import java.util.*


fun List<UserEntity>.toUserDetail(): List<DetailResponse> {
    val users = ArrayList<DetailResponse>()
    this.map {
        val user = DetailResponse(
            id = it.id,
            avatar_url = it.avatar_url,
            bio = it.bio,
            blog = it.blog,
            company = it.company,
            created_at = it.created_at,
            email = it.email,
            events_url = it.events_url,
            followers = it.followers,
            followers_url = it.followers_url,
            following = it.following,
            following_url = it.following_url,
            gists_url = it.gists_url,
            gravatar_id = it.gravatar_id,
            hireable = it.hireable,
            html_url = it.html_url,
            location = it.location,
            login = it.login,
            name = it.name,
            node_id = it.node_id,
            organizations_url = it.organizations_url,
            public_gists = it.public_gists,
            public_repos = it.public_repos,
            received_events_url = it.received_events_url,
            repos_url = it.repos_url,
            site_admin = it.site_admin,
            starred_url = it.starred_url,
            subscriptions_url = it.subscriptions_url,
            twitter_username = it.twitter_username,
            type = it.type,
            updated_at = it.updated_at,
            url = it.url
        )
        users.add(user)
    }
    return users
}

fun UserEntity.toUserDetail(): DetailResponse =
    DetailResponse(
        id = id,
        avatar_url = avatar_url,
        bio = bio,
        blog = blog,
        company = company,
        created_at = created_at,
        email = email,
        events_url = events_url,
        followers = followers,
        followers_url = followers_url,
        following = following,
        following_url = following_url,
        gists_url = gists_url,
        gravatar_id = gravatar_id,
        hireable = hireable,
        html_url = html_url,
        location = location,
        login = login,
        name = name,
        node_id = node_id,
        organizations_url = organizations_url,
        public_gists = public_gists,
        public_repos = public_repos,
        received_events_url = received_events_url,
        repos_url = repos_url,
        site_admin = site_admin,
        starred_url = starred_url,
        subscriptions_url = subscriptions_url,
        twitter_username = twitter_username,
        type = type,
        updated_at = updated_at,
        url = url
    )

fun UserEntity.toUser(): User =
    User(
        id = id,
        avatar_url = avatar_url,
        events_url = events_url,
        followers_url = followers_url,
        following_url = following_url,
        gists_url = gists_url,
        gravatar_id = gravatar_id,
        html_url = html_url,
        login = login.toString(),
        node_id = node_id,
        organizations_url = organizations_url,
        received_events_url = received_events_url,
        repos_url = repos_url,
        site_admin = site_admin,
        starred_url = starred_url,
        subscriptions_url = subscriptions_url,
        type = type,
        url = url
    )

fun DetailResponse.toUserEntity(): UserEntity =
    UserEntity(
        id = id,
        avatar_url = avatar_url,
        bio = bio,
        blog = blog,
        company = company,
        created_at = created_at,
        email = email,
        events_url = events_url,
        followers = followers,
        followers_url = followers_url,
        following = following,
        following_url = following_url,
        gists_url = gists_url,
        gravatar_id = gravatar_id,
        hireable = hireable,
        html_url = html_url,
        location = location,
        login = login,
        name = name,
        node_id = node_id,
        organizations_url = organizations_url,
        public_gists = public_gists,
        public_repos = public_repos,
        received_events_url = received_events_url,
        repos_url = repos_url,
        site_admin = site_admin,
        starred_url = starred_url,
        subscriptions_url = subscriptions_url,
        twitter_username = twitter_username,
        type = type,
        updated_at = updated_at,
        url = url
    )