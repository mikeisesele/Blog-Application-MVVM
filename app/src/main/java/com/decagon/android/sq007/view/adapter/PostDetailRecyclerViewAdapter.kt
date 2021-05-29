package com.decagon.android.sq007.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.model.Comments

class PostDetailRecyclerViewAdapter (var context: Context) : RecyclerView.Adapter<PostDetailRecyclerViewAdapter.DynamicViewHolder>() {


        private var recyclerList: List<Comments>? = arrayListOf()  // list for recycler view to work with

        fun setAdapterList(recyclerList: List<Comments>) {  // input point for list detail
            this.recyclerList = recyclerList
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicViewHolder {
            val recyclerLayout = LayoutInflater.from(parent.context).inflate(R.layout.comment_model, parent, false) // get and return model to populate recycler view
            return DynamicViewHolder(recyclerLayout)
        }

        override fun getItemCount(): Int {
            return recyclerList!!.size // get size of recycler list
        }

        inner class DynamicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // bind xml to variables
            val name: TextView = itemView.findViewById(R.id.name)
            val email: TextView = itemView.findViewById(R.id.email)
            val body: TextView = itemView.findViewById(R.id.post)
        }


        override fun onBindViewHolder(viewHolder: DynamicViewHolder, position: Int) {   // Choose the view to bind
            viewHolder.body.text = recyclerList!![position].body
            viewHolder.email.text = recyclerList!![position].email
            viewHolder.name.text = recyclerList!![position].name

        }
    }
