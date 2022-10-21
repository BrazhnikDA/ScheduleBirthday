package com.example.shedulebirthday.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shedulebirthday.R
import com.example.shedulebirthday.model.UserFullModel
import com.example.shedulebirthday.model.UserShortModel
import com.example.shedulebirthday.service.LoadSaveData


class ListUsersBirthdayFragment : Fragment(), ItemClickListener {

    var list = ArrayList<UserFullModel>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_users_birthday, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)!!
        recyclerView.layoutManager = GridLayoutManager(context, 2)


        loadSaveUsers()
        displayList(list)
    }

    private fun loadSaveUsers() {
        list = LoadSaveData().getSaveData()
    }

    private fun displayList(listFullUser: ArrayList<UserFullModel>) {
        recyclerView.adapter = ListUserBirthdayAdapter(convertFullModelToShort(listFullUser), this)
    }

    override fun onCellClickListener(idItem: Int) {
        //Toast.makeText(this, "Click", Toast.LENGTH_SHORT)
        println("Click!")
    }

    override fun onCellLongClickListener(idItem: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        fun convertFullModelToShort(full: ArrayList<UserFullModel>): ArrayList<UserShortModel> {
            val short = ArrayList<UserShortModel>()
            for (item in full) {
                short.add(UserShortModel(item.id, item.name, item.picture))
            }
            return short
        }
    }
}