package com.example.schedulebirthday.view

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulebirthday.R
import com.example.schedulebirthday.database.room.entity.UserEventEntity
import com.example.schedulebirthday.databinding.FragmentListUsersBirthdayBinding
import com.example.schedulebirthday.model.UserFullModel
import com.example.schedulebirthday.service.LoadSaveData
import com.example.schedulebirthday.view.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragment_list_users_birthday.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class ListUsersBirthdayFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentListUsersBirthdayBinding? = null
    private val binding get() = _binding!!

    private val scope = CoroutineScope(Dispatchers.IO)
    private var listUsers = MutableLiveData<List<UserFullModel>>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUsersBirthdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)!!
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        handlerClick()
        handlerInput()
        loadSaveUsers()
    }

    private fun loadSaveUsers() {
        scope.launch {
            LoadSaveData(listUsers).getSaveUsers()
        }
        listUsers.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                displayList(it)
            }
        }
    }

    private fun displayList(listFullUser: List<UserFullModel>) {
        recyclerView.adapter = ListUserBirthdayAdapter((listFullUser), this)
    }

    override fun onCellClickListener(idItem: Long) {
        val reallyID = idItem.toInt() - 1
        binding.profileBackground.visibility = View.VISIBLE
        binding.showProfileWrapper.visibility = View.VISIBLE
        binding.textViewUser.text = listUsers.value?.get(reallyID)?.name ?: "Имя не найдено"


        binding.textViewDateOfBorn.text =
            listUsers.value!![reallyID].day + "/" + listUsers.value!![reallyID].months + "/" + listUsers.value!![reallyID].year + " " + listUsers.value!![reallyID].age + " лет"


        binding.showProfileWrapper.setOnClickListener {
            binding.profileBackground.visibility = View.GONE
            binding.showProfileWrapper.visibility = View.GONE
        }
    }

    override fun onCellLongClickListener(idItem: Long) {
        TODO("Not yet implemented")
    }

    private fun parseLocalDateOrNull(text: String, formatter: DateTimeFormatter): Boolean {
        return try {
            LocalDate.parse(text, formatter)
            true
        } catch (ex: DateTimeParseException) {
            Toast.makeText(context, "Дата неверна!", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun calculateYear(day: String, months: String, year: String): Int {
        val dateString = day + months + year
        val from = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("ddMMyyyy"))
        val today = LocalDate.now()
        return Period.between(from, today).years
    }


    private fun handlerInput() {
        binding.editTextDate.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            val str = editTextDate.text.toString()

            if (str.length == 1) {
                if (str.toInt() > 4) {
                    binding.editTextDate.setText("0$str.")
                    binding.editTextDate.setSelection(binding.editTextDate.length())
                }
            }

            if (str.length == 2 || str.length == 5) {
                binding.editTextDate.setText("$str.")
                binding.editTextDate.setSelection(binding.editTextDate.length())
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                return@OnKeyListener true
            }
            false
        })
    }

    // Handler click on button to screen (menu, GoHome, Add, Sort)
    private fun handlerClick() {
        binding.textViewButtonAddNewBirthday.setOnClickListener {
            binding.newBackground.visibility = View.VISIBLE
            binding.showAddNewWrapper.visibility = View.VISIBLE
        }
        binding.buttonAddNew.setOnClickListener {
            // Проверка полей
            if (binding.editTextName.text.toString() == "" || binding.editTextDate.text.toString() == "") {
                Toast.makeText(context, "Заполните поля", Toast.LENGTH_SHORT).show()
            } else {
                val name = binding.editTextName.text.toString()
                val date = binding.editTextDate.text.toString()
                val parseDate = date.split('.')
                val day = parseDate[0]
                val month = parseDate[1]
                val year = parseDate[2]

                if (parseLocalDateOrNull(
                        day + month + year,
                        DateTimeFormatter.ofPattern("ddMMyyyy")
                    )
                ) {

                    val yearsBetween = calculateYear(day, month, year).toString()
                    val user = UserEventEntity(name, "null", day, month, year, yearsBetween)
                    scope.launch {
                        LoadSaveData(listUsers).setSaveUser(user)
                    }

                    binding.newBackground.visibility = View.GONE
                    binding.showAddNewWrapper.visibility = View.GONE
                    Toast.makeText(context, "Успех!", Toast.LENGTH_SHORT).show()
                    // Update RecyclerView
                    loadSaveUsers()
                } else {
                    Toast.makeText(context, "Дата указана неверно!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.newBackground.setOnClickListener {
            binding.newBackground.visibility = View.GONE
            binding.showAddNewWrapper.visibility = View.GONE
        }
        binding.textViewButtonOpenHomeScreenMenu.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        binding.textViewButtonSortWithAlphabeticallyForHomeScreen.setOnClickListener {
            displayList(listUsers.value!!.sortedBy { it.name })
        }

        binding.imageViewButtonHamburgerMenuClose.setOnClickListener {
            binding.menuBackground.visibility = View.GONE
            binding.menuButtonsWrapper.visibility = View.GONE
        }
        binding.imageViewButtonHamburgerMenu.setOnClickListener {
            binding.menuBackground.visibility = View.VISIBLE
            binding.menuButtonsWrapper.visibility = View.VISIBLE
        }

        binding.textViewButtonSettings.setOnClickListener {
            // Скрыть меню перед переходом
            binding.menuBackground.visibility = View.GONE
            binding.menuButtonsWrapper.visibility = View.GONE
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.textViewButtonReviews.setOnClickListener {
            //TODO Открыть другую активность
        }
        binding.textViewButtonCallAFriend.setOnClickListener {
            //TODO Открыть другую активность
        }
        binding.menuBackground.setOnClickListener {
            // Nothing
        }
    }

    companion object {
        //TODO Переместить в класс юзерфуллмодель!!!
        /*fun convertFullModelToShort(full: List<UserFullModel>): ArrayList<UserShortModel> {
            val short = ArrayList<UserShortModel>()
            for (item in full) {
                short.add(UserShortModel(item.name, item.picture))
            }
            return short
        }*/
    }
}