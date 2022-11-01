package com.example.schedulebirthday.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulebirthday.R
import com.example.schedulebirthday.model.UserModel
import com.example.schedulebirthday.model.UserModel.Companion.calculateRemainingDaysToBirthday
import com.makeramen.roundedimageview.RoundedImageView
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class ListUserBirthdayAdapter(
    private val listUser: List<UserModel>,
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
        val period = calculateRemainingDaysToBirthday(listUser[position].day, listUser[position].months)

        holder.name.text = listUser[position].name + "\n" + period + " дней"

       // val imgFile = File(listUser[position].picture)
       // val bitmap: Bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)

       // holder.picture.setImageURI(Uri.parse(listUser[position].picture))
       // holder.picture.setImageBitmap(bitmap)

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