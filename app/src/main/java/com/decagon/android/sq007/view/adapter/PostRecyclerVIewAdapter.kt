package com.decagon.android.sq007.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.R.id.materialCardView
import com.decagon.android.sq007.model.Post
import com.decagon.android.sq007.interfaces.ClickListenerInterface

class PostRecyclerVIewAdapter(var context: Context, var clickListener: ClickListenerInterface) : RecyclerView.Adapter<PostRecyclerVIewAdapter.DynamicViewHolder>() {

    private var recyclerList: List<Post>? = arrayListOf()     // list for recycler view to work with

    fun setAdapterList(recyclerList: List<Post>) {  // input point for list detail
        this.recyclerList = recyclerList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicViewHolder {
        val recyclerLayout = LayoutInflater.from(parent.context).inflate(R.layout.post_model, parent, false) // get and return model to populate recycler view
        return DynamicViewHolder(recyclerLayout)
    }

    override fun getItemCount(): Int {
        return recyclerList!!.size // get size of recycler list
    }

    inner class DynamicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // bind xml to variables
        val title: TextView = itemView.findViewById(R.id.title)
        val post: TextView = itemView.findViewById(R.id.post)
        val card: CardView = itemView.findViewById(materialCardView)
    }

    override fun onBindViewHolder(viewHolder: DynamicViewHolder, position: Int) { // Choose the view to bind
        viewHolder.post.text = recyclerList!![position].body
        viewHolder.title.text = recyclerList!![position].title

        viewHolder.card.setOnClickListener{
            clickListener.onItemClicked(position, viewHolder.post.text as String, recyclerList!![position].userId)
        }
    }
}