package com.example.schedulebirthday.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulebirthday.R
import com.example.schedulebirthday.model.UserFullModel
import com.makeramen.roundedimageview.RoundedImageView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class ListUserBirthdayAdapter(
    private val listUser: List<UserFullModel>,
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
        val sdf = SimpleDateFormat("yyyy")
        val currentDate = sdf.format(Date())
        val dateString = listUser[position].day + listUser[position].months + currentDate
        var from = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("ddMMyyyy"))
        val today = LocalDate.now()
        var period = ChronoUnit.DAYS.between(today, from)

        if(period < 0) {
            from = from.plusYears(1)
            period = ChronoUnit.DAYS.between(today, from)
        }


        holder.name.text = listUser[position].name + "\n" + period + " дней"
        //holder.picture.setImageDrawable(getDrawable(this, R.drawable.unnamed))

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(listUser[position].id.toLong())
        }
        /*holder.itemView.setOnLongClickListener {
            cellClickListener.onCellLongClickListener(listUser[position].id)
        }*/
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}