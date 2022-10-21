package com.example.shedulebirthday.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.shedulebirthday.R
import com.example.shedulebirthday.model.UserFullModel
import com.example.shedulebirthday.model.UserShortModel
import com.makeramen.roundedimageview.RoundedImageView

class ListUserBirthdayAdapter(
    private val listUser: List<UserShortModel>,
    private val cellClickListener: ItemClickListener
) : RecyclerView.Adapter<ListUserBirthdayAdapter.RatingViewHolder>() {

    class RatingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.textViewUser)
        val picture: RoundedImageView = itemView.findViewById(R.id.imageViewUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_birthday, parent, false)
        return RatingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        holder.name.text = listUser[position].name
        //holder.picture.setImageDrawable(getDrawable(this, R.drawable.unnamed))

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(listUser[position].id)
        }
        /*holder.itemView.setOnLongClickListener {
            cellClickListener.onCellLongClickListener(listUser[position].id)
        }*/
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}