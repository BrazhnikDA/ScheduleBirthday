package com.example.schedulebirthday.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulebirthday.R
import com.example.schedulebirthday.database.room.entity.UserEventEntity
import com.example.schedulebirthday.databinding.FragmentListUsersBirthdayBinding
import com.example.schedulebirthday.model.UserModel
import com.example.schedulebirthday.repository.*
import com.example.schedulebirthday.utilities.convertStringEditTextToArrayStringDate
import com.example.schedulebirthday.utilities.convertStringEditTextToStringDate
import com.example.schedulebirthday.utilities.displayToast
import com.example.schedulebirthday.view.settings.SettingsActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_list_users_birthday.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.*


class ListUsersBirthdayFragment : Fragment(), ItemClickListener {

    //Status sort
    private var statusSort: StatusSort = StatusSort.DATE_UP

    // Path to image
    private lateinit var picturePath: String

    private var _binding: FragmentListUsersBirthdayBinding? = null
    private val binding get() = _binding!!

    private val scope = CoroutineScope(Dispatchers.IO)
    private var listUsers = MutableLiveData<List<UserModel>>()
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

    private fun displayList(listFullUser: List<UserModel>) {
        recyclerView.adapter = ListUserBirthdayAdapter((listFullUser), this)
    }

    override fun onCellClickListener(idItem: Long) {
        val reallyID = idItem.toInt() - 1
        binding.profileBackground.visibility = View.VISIBLE
        binding.showProfileWrapper.visibility = View.VISIBLE
        binding.textViewUser.text = listUsers.value?.get(reallyID)?.name


        binding.textViewDateOfBorn.text = String.format(
            getString(R.string.show_detail_profile_age),
            listUsers.value!![reallyID].day,
            listUsers.value!![reallyID].months,
            listUsers.value!![reallyID].year,
            listUsers.value!![reallyID].age,
            calculateYear(
                listUsers.value!![reallyID].day,
                listUsers.value!![reallyID].months,
                listUsers.value!![reallyID].year
            ).toString()
        )

        binding.showProfileWrapper.setOnClickListener {
            binding.profileBackground.visibility = View.GONE
            binding.showProfileWrapper.visibility = View.GONE
        }
    }

    override fun onCellLongClickListener(idItem: Long) {
        TODO("Not yet implemented")
    }

    private fun handlerInput() {
        binding.editTextDate.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            val str = editTextDate.text.toString()

            if (str.length == 1) {
                if (str.toInt() >= 4) {
                    binding.editTextDate.setText(
                        String.format(
                            getString(R.string.correction_input_date_null_start_point_end),
                            str
                        )
                    )
                    binding.editTextDate.setSelection(binding.editTextDate.length())
                }
            }
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (str.length == 6) {
                    binding.editTextDate.setText(str.removeRange(5, 6))
                    binding.editTextDate.setSelection(binding.editTextDate.length())
                }
                if (str.length == 3) {
                    binding.editTextDate.setText(str.removeRange(2, 3))
                    binding.editTextDate.setSelection(binding.editTextDate.length())
                }
                if (str.length == 2 or 5) {
                    binding.editTextDate.setText(str.removeRange(str.length, str.length))
                    binding.editTextDate.setSelection(binding.editTextDate.length())
                }
            }

            if (str.length == 2 || str.length == 5 && keyCode != KeyEvent.KEYCODE_DEL) {
                binding.editTextDate.setText(
                    String.format(
                        getString(R.string.correction_input_date_point_end),
                        str
                    )
                )
                binding.editTextDate.setSelection(binding.editTextDate.length())
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                return@OnKeyListener true
            }
            false
        })
    }

    //region Handler Click

    // Handler click on button to screen (menu, GoHome, Add, Sort)
    private fun handlerClick() {
        clickHamburgerMenu()
        clickAddNewBirthday()
        clickOpenReviews()
        clickOpenSettings()
        clickGoToHomeScreen()
        clickOpenCallAFriend()
        clickSortListUsers()
    }

    private fun clickHamburgerMenu() {
        // Click Open Menu
        binding.imageViewButtonHamburgerMenu.setOnClickListener {
            binding.menuBackground.visibility = View.VISIBLE
            binding.menuButtonsWrapper.visibility = View.VISIBLE
        }
        // Click Close Menu
        binding.imageViewButtonHamburgerMenuClose.setOnClickListener {
            binding.menuBackground.visibility = View.GONE
            binding.menuButtonsWrapper.visibility = View.GONE
        }
        // When you click on the background under the menu, do nothing
        binding.menuBackground.setOnClickListener {
            // Nothing
        }
    }

    private fun clickGoToHomeScreen() {
        //TODO FIX MEMORY LEAK!!!
        binding.textViewButtonOpenHomeScreenMenu.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickAddNewBirthday() {
        // Show menu for add new user
        binding.textViewButtonAddNewBirthday.setOnClickListener {
            binding.newBackground.visibility = View.VISIBLE
            binding.wrapperAddNewImage.visibility = View.VISIBLE
        }
        // Handler click add new User
        binding.buttonAddNew.setOnClickListener {
            val etName = binding.editTextName.text.toString()
            val etDate = binding.editTextDate.text.toString()
            // Check field
            if (etName.isEmpty() || etDate.isEmpty()
            ) context?.displayToast("Заполните поля")
            else {
                val date = convertStringEditTextToStringDate(etDate, '.')
                val arrayDate = convertStringEditTextToArrayStringDate(etDate, '.')

                if (parseLocalDateOrNull(date, DateTimeFormatter.ofPattern("ddMMyyyy"))) {
                    val yearsBetween = calculateYear(date).toString()
                    val user = UserEventEntity(
                        etName,
                        picturePath ?: "null",
                        arrayDate[0],
                        arrayDate[1],
                        arrayDate[2],
                        yearsBetween
                    )

                    scope.launch {
                        LoadSaveData(listUsers).setSaveUser(user)
                    }

                    binding.newBackground.visibility = View.GONE
                    binding.wrapperAddNewImage.visibility = View.GONE
                    context?.displayToast("Успех!")

                    // Update RecyclerView
                    loadSaveUsers()
                } else {
                    context?.displayToast("Дата указана неверно!")
                }
            }
        }
        binding.imageViewNewImage.setOnClickListener {
            openGalleryForImage()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        // TODO FIX deprecated
        startActivityForResult(intent, REQUEST_CODE)
    }

    // TODO FIX deprecated
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            binding.imageViewNewImage.setImageURI(data!!.data)
            uploadImageToFirebase(data.data!!)
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() +".png"

            val refStorage = FirebaseStorage
                .getInstance()
                .getReferenceFromUrl("gs://schedule-birthday.appspot.com")
                .child(fileName)

            refStorage.putFile(fileUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        val imageUrl = it.toString()
                        picturePath = imageUrl
                    }
                }

                .addOnFailureListener { e ->
                    print(e.message)
                    picturePath = "null"
                }
        }
    }


    private fun clickSortListUsers() {
        // Sort user on Alphabetically
        binding.textViewButtonSortWithAlphabeticallyForHomeScreen.setOnClickListener {
            statusSort = when (statusSort) {
                StatusSort.ALPHABETICALLY -> {
                    StatusSort.DATE_DOWN
                }
                StatusSort.DATE_DOWN -> {
                    StatusSort.DATE_UP
                }
                StatusSort.DATE_UP -> {
                    StatusSort.ALPHABETICALLY
                }
            }

            displayList(StatusSort.sortList(statusSort, listUsers.value!!))
        }
    }

    private fun clickOpenSettings() {
        binding.textViewButtonSettings.setOnClickListener {
            // Скрыть меню перед переходом
            binding.menuBackground.visibility = View.GONE
            binding.menuButtonsWrapper.visibility = View.GONE
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickOpenReviews() {
        binding.textViewButtonReviews.setOnClickListener {
            //TODO Открыть другую активность
        }
    }

    private fun clickOpenCallAFriend() {
        binding.textViewButtonCallAFriend.setOnClickListener {
            //TODO Открыть другую активность
        }
    }

    //endregion

    companion object {
        private const val REQUEST_CODE = 100
    }
}