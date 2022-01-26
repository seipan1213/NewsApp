package jp.ac.dhw.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load


class ArticleDetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_KEY = "ArticleData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        // データ受け取り
        val intent = intent
        val article = intent.getSerializableExtra(EXTRA_KEY) as Article

        // UI部品取得
        val image = findViewById<ImageView>(R.id.article_detail_image)
        val title = findViewById<TextView>(R.id.article_detail_title)
        val description = findViewById<TextView>(R.id.article_detail_description)
        val content = findViewById<TextView>(R.id.article_detail_content)
        val sourceName = findViewById<TextView>(R.id.article_detail_source_name)
        val author = findViewById<TextView>(R.id.article_detail_author)
        val url = findViewById<TextView>(R.id.article_detail_url)
        val date = findViewById<TextView>(R.id.article_detail_date)
        val browseButton = findViewById<Button>(R.id.article_detail_browse_button)

        // UI設定
        image.load(article.urlToImage)
        title.text = article.title
        description.text = article.description
        content.text = article.content
        author.text = article.author
        sourceName.text = article.source.name
        url.text = article.url
        date.text = article.publishedAt

        // ブラウズ用のボタン設定
        browseButton.setOnClickListener {
            val uri = Uri.parse(article.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}