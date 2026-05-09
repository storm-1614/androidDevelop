package com.example.recycleview

import com.example.recyclerviewtest.Fruit

class FruitAdapter(val fruitList: List<Fruit>) : androidx.recyclerview.widget.RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    inner class ViewHolder(view: android.view.View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val fruitImage: android.widget.ImageView = view.findViewById(R.id.fruitImage)
        val fruitName: android.widget.TextView = view.findViewById(R.id.fruitName)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view = android.view.LayoutInflater.from(parent.context).inflate(R.layout.fruit_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            android.widget.Toast.makeText(parent.context, "you clicked view ${fruit.name}", android.widget.Toast.LENGTH_SHORT).show()
        }
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            android.widget.Toast.makeText(parent.context, "you clicked image ${fruit.name}", android.widget.Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }

    override fun getItemCount() = fruitList.size
}