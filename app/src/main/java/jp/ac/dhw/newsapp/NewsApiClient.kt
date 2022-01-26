package jp.ac.dhw.newsapp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsApiClient {
    //エラーが出る場合もあるが、ビルドすると消える
    private val APIKEY = BuildConfig.NEWS_API_KEY // secrets.propertiesをに書く
    private val country = "jp"
    private val category = ""
    private val sources = ""
    private val q = ""
    private val pageSize = 100
    private val page = 1

    private fun NewsApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun getNews(): Response<NewsResponse> {
        val service = NewsApiClient().create(NewsService::class.java)
        return service.getNewsTopHeadlines(APIKEY, country, pageSize).execute()
    }
}