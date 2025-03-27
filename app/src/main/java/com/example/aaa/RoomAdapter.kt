package com.example.aaa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RoomAdapter(private val rooms: List<Room>, private val onRoomClick: (Room) -> Unit) :
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomImage: ImageView = itemView.findViewById(R.id.roomImage)
        val roomName: TextView = itemView.findViewById(R.id.roomName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.room_item_layout, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.roomName.text = room.roomType

        // Load Image
        if (room.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(room.imageUrl).into(holder.roomImage)
        } else {
            holder.roomImage.setImageResource(R.drawable.sampleimage1)
        }

        // Handle Click Event
        holder.itemView.setOnClickListener {
            onRoomClick(room)
        }
    }

    override fun getItemCount() = rooms.size
}
