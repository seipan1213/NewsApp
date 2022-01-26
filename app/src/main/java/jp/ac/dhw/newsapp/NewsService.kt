package jp.ac.dhw.newsapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines")
    fun getNewsTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        //@Query("category") category: String,
        //@Query("sources") sources: String,
        //@Query("q") q: String,
        @Query("pageSize") pageSize: Int,
        //@Query("page") page: String,
    ): Call<NewsResponse>
}

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
)


