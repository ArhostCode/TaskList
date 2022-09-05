package space.ardyc.taskandroidapp.util

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URL

object RequestUtils {

    fun getBaseRequestUtilsBuilder(): RequestUtilsBuilder {
        return RequestUtilsBuilder().getBaseBuild()
    }

    fun getBaseClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    fun getNoRedirectClient(): OkHttpClient {
        return OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false).build()
    }
}

class RequestUtilsBuilder {

    var requestBuilder = Request.Builder()

    fun getBaseBuild(): RequestUtilsBuilder {
        requestBuilder
            .addHeader("Accept", "application/json, text/plain, */*")
            .addHeader("Accept-Encoding", "gzip, deflate, br")
            .addHeader("Connection", "keep-alive")
            .addHeader("User-Agent", "TaskClient")
        return this
    }

    fun url(url: URL): RequestUtilsBuilder {
        requestBuilder.url(url)
        return this
    }

    fun post(body: RequestBody): RequestUtilsBuilder {
        requestBuilder.post(body)
        return this
    }

    fun get(): RequestUtilsBuilder {
        requestBuilder.get()
        return this
    }

    fun withCookie(cookie: String): RequestUtilsBuilder {
        requestBuilder.addHeader("Cookie", cookie)
        return this
    }

    fun withHeader(name: String, value: String): RequestUtilsBuilder {
        requestBuilder.addHeader(name, value)
        return this
    }

    fun build(): Request {
        return requestBuilder.build()
    }
}
