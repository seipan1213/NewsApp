package jp.ac.dhw.newsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    private lateinit var articleList: List<Article>
    private val errorText = "エラーが発生しました。再起動してください"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI設定用のハンドラー
        val handler = Handler(Looper.getMainLooper())
        val runnableSetUI = object : Runnable {
            override fun run() {
                setUI()
            }
        }

        // 別スレッドでAPI処理
        thread {
            try {
                val response = NewsApiClient.getNews()
                val body = response.body()
                if (response.isSuccessful && body !== null) {
                    articleList = body.articles
                    handler.post(runnableSetUI)
                }
            } catch (e: Exception) {
                Toast.makeText(this, errorText, errorText.length).show()
                throw Error("API ERROR")
            }
        }
    }

    // UI設定
    private fun setUI() {
        // RecyclerViewの取得
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_article)
        val adapter = ArticleRecyclerViewAdapter(articleList)
        // LayoutManagerの設定
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        // クリックイベント設定
        val mainContext = this
        adapter.setOnArticleCellClickListener(
            object : ArticleRecyclerViewAdapter.OnArticleCellClickListener {
                override fun onItemClick(article: Article) {
                    val intent =
                        Intent(mainContext, ArticleDetailActivity::class.java)

                    intent.putExtra(ArticleDetailActivity.EXTRA_KEY, article)
                    startActivity(intent)
                }
            }
        )
    }
}
