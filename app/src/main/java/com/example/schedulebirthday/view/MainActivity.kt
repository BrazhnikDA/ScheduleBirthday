package com.example.schedulebirthday.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.schedulebirthday.R
import com.example.schedulebirthday.database.room.creator.BirthdayDatabase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BirthdayDatabase.initAllTableDB(applicationContext)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val mainFragment = ListUsersBirthdayFragment()
        ft.replace(R.id.fragment, mainFragment)
        ft.commit()
    }
}