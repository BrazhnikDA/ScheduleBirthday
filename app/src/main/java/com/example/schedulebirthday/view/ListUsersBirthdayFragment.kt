package com.example.schedulebirthday.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulebirthday.R
import com.example.schedulebirthday.databinding.FragmentListUsersBirthdayBinding
import com.example.schedulebirthday.model.UserFullModel
import com.example.schedulebirthday.model.UserShortModel
import com.example.schedulebirthday.service.LoadSaveData
import com.example.schedulebirthday.view.settings.SettingsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListUsersBirthdayFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentListUsersBirthdayBinding? = null
    private val binding get() = _binding!!

    private val scope = CoroutineScope(Dispatchers.IO)
    private var listUsers = MutableLiveData<List<UserFullModel>>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListUsersBirthdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)!!
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        handlerClick()
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
        binding.profileBackground.visibility = View.VISIBLE
        binding.showProfileWrapper.visibility = View.VISIBLE
        binding.textViewUser.text = listUsers.value!![idItem.toInt()].name


        binding.textViewDateOfBorn.text =
            listUsers.value!![idItem.toInt()].day + "/" + listUsers.value!![idItem.toInt()].months + "/" + listUsers.value!![idItem.toInt()].year + " " + listUsers.value!![idItem.toInt()].age + " лет"


        binding.showProfileWrapper.setOnClickListener {
            binding.profileBackground.visibility = View.GONE
            binding.showProfileWrapper.visibility = View.GONE
        }
    }

    override fun onCellLongClickListener(idItem: Long) {
        TODO("Not yet implemented")
    }

    // Handler click on button to screen (menu, GoHome, Add, Sort)
    private fun handlerClick() {
        binding.textViewButtonAddNewBirthday.setOnClickListener {
            //TODO Добавить новое день рождение
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
        fun convertFullModelToShort(full: List<UserFullModel>): ArrayList<UserShortModel> {
            val short = ArrayList<UserShortModel>()
            for (item in full) {
                short.add(UserShortModel(item.id, item.name, item.picture))
            }
            return short
        }
    }
}