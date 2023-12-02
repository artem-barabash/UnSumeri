package com.example.unsumeri.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R
import com.example.unsumeri.domain.entities.Article
import com.example.unsumeri.domain.entities.Category

class ArticleAdapter (
    private val context: Context,
    private val dataset: List<Article>?
    ): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textTitle)
        val categoryText: TextView = view.findViewById(R.id.categoryTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleAdapter.ArticleViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset?.size ?: 0
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = dataset?.get(position)

        holder.title.text = article!!.title
        //holder.categoryText.text = article!!.category!!.name
    }
}