package de.grademanager.core.network.di

import de.grademanager.core.network.SessionManager
import de.grademanager.core.network.model.RefreshAccessTokenBody
import de.grademanager.core.network.model.RefreshAccessTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val KEY_TOKEN_STORAGE = "de.grademanager.tokenstorage"

private const val KEY_ACCESS_TOKEN = "de.grademanager.tokenstorage.AccessToken"
private const val KEY_REFRESH_TOKEN = "de.grademanager.tokenstorage.AccessToken"

val NetworkModule = module {
    single {
        SessionManager(context = androidContext())
    }

    single<HttpClient> {
        val sessionManager = get<SessionManager>()

        HttpClient(OkHttp) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "orga-life.de"
                }

                contentType(ContentType.Application.Json)
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = sessionManager.getAccessToken()

                        if (accessToken.isNotBlank()) {
                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = sessionManager.getRefreshToken()
                            )
                        } else {
                            null
                        }
                    }

                    refreshTokens {
                        try {
                            val response = client.post {
                                url("api/auth/refresh-token/")
                                setBody(
                                    body = RefreshAccessTokenBody(
                                        refreshToken = sessionManager.getRefreshToken()
                                    )
                                )

                                markAsRefreshTokenRequest()
                            }.body<RefreshAccessTokenResponse>()

                            val accessToken = response.accessToken.token
                            val refreshToken = response.refreshToken

                            sessionManager.setAccessToken(accessToken)
                            sessionManager.setRefreshToken(accessToken)

                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken
                            )

                            null
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
            }
        }
    }
}