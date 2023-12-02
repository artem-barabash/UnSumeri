package com.example.unsumeri.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R
import com.example.unsumeri.domain.entities.Category

class CategoryAdapter(
    private val context: Context,
    private val dataset: List<Category>?
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){


    class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.titleCategory)
        val recyclerView: RecyclerView = view.findViewById(R.id.subcategoryRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.categories_item, parent, false)
        return CategoryViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset?.size ?: 0
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = dataset?.get(position)
        holder.title.text = item!!.name

        holder.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        holder.recyclerView.adapter = CategoryAdapter(context, item.subCategories)

        println("subcategory ${item.subCategories}")


    }
}