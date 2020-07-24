package com.agarifullin.practiceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private var items: List<OrderItem>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = items[position]
        holder.what?.text = orderItem.what
        holder.who?.text = orderItem.who
        holder.deadLine?.text = "Дата получения заказа: ${orderItem.deadLine}"
        holder.from?.text = "Дедлайн: ${orderItem.from}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_adapter, parent, false)

        return ViewHolder(itemView)
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var what: TextView? = null
        var who: TextView? = null
        var from:TextView? = null
        var deadLine :TextView? = null

        init {
            this.what = row.findViewById(R.id.what)
            this.who = row.findViewById(R.id.who)
            this.from = row.findViewById(R.id.from)
            this.deadLine = row.findViewById(R.id.deadLine)
        }
    }
}