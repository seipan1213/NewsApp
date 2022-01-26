package jp.ac.dhw.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ArticleRecyclerViewAdapter(private val articleList: List<Article>) :
    RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleRecyclerViewHolder>() {

    private lateinit var listener: OnArticleCellClickListener

    interface OnArticleCellClickListener {
        fun onItemClick(article: Article)
    }

    // リスナー設定
    fun setOnArticleCellClickListener(listener: OnArticleCellClickListener) {
        // 定義した変数listenerに実行したい処理を引数で渡す（BookListFragmentで渡している）
        this.listener = listener
    }

    // UI部品の要素のクラス
    class ArticleRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.article_title)
        val image: ImageView = view.findViewById(R.id.article_image)
        val author: TextView = view.findViewById(R.id.article_author)
        val url: TextView = view.findViewById(R.id.article_url)
        val date: TextView = view.findViewById(R.id.article_data)
    }

    // UI部品を生成
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ArticleRecyclerViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_view_article_list, viewGroup, false)

        return ArticleRecyclerViewHolder(view)
    }

    // UI部品の設定
    override fun onBindViewHolder(viewHolder: ArticleRecyclerViewHolder, index: Int) {
        val article = articleList[index]
        viewHolder.title.text = article.title
        viewHolder.image.load(article.urlToImage)
        viewHolder.author.text = article.author
        viewHolder.url.text = article.url
        viewHolder.date.text = article.publishedAt
        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(article)
        }
    }

    override fun getItemCount(): Int = articleList.size
}