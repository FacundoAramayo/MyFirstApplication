package com.fcode.myfirstapplication.ui.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fcode.myfirstapplication.domain.Article
import com.fcode.myfirstapplication.R
import com.fcode.myfirstapplication.utils.loadFromUrl

class ArticleAdapter(private val onClick: (Article) -> Unit): RecyclerView.Adapter<ArticleAdapter.ArticleAdapterViewHolder>() {

    var articles = listOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return  ArticleAdapterViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ArticleAdapterViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleAdapterViewHolder(view: View, val onClick: (Article) -> Unit): RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val image: ImageView = view.findViewById(R.id.item_news_image)
        private var currentArticle: Article? = null

        init {
            view.setOnClickListener {
                currentArticle?.let {
                    onClick(it)
                }
            }
        }

        fun bind(article: Article) {
            currentArticle = article
            title.text = article.title
            image.loadFromUrl(article.urlToImage)
        }
    }

}