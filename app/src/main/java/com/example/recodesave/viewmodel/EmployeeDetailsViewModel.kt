package com.example.recodesave.viewmodel

import android.app.DatePickerDialog
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.recodesave.R
import com.example.recodesave.repository.UserRepository
import com.example.recodesave.roomdb.User
import kotlinx.coroutines.launch
import java.util.Calendar.*


class EmployeeDetailsViewModel(private val repository: UserRepository) : ViewModel() {
    var mutableLiveData = MutableLiveData<String>()
    var name: String? = null
    var salary: String? = null
    var date: String? = null
    var email: String? = null
    var number: String? = null
    var address: String? = null
    var department: String? = null
    val itemPosition = MutableLiveData<Int>()
    val items = arrayListOf(
        "Technical",
        "Support",
        "Research and Development",
        "Marketing",
        "Human Resource"
    )



    // selected item
    private val selectItem
        get() = itemPosition.value?.let {
            items.get(it)
        }

    //open fragment click on button
    fun openListFragment(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_employeeDetailsFragment_to_employeesListFragment)
    }

    //open calender click on button
    fun openCalender(view: View) {
        val c = getInstance()
        val year = c.get(YEAR)
        val month = c.get(MONTH)
        val day = c.get(DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            view.context, { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
                mutableLiveData.value = """$dayOfMonth - ${monthOfYear + 1} - $year"""
            }, year, month, day
        )
        dpd.show()
    }

    //save data button click
    fun saveDetails(view: View) {
        if (name.isNullOrEmpty()) {
            toast(view, "Name should not be empty...")
        } else if (salary.isNullOrEmpty()) {
            toast(view, "Salary should not be empty...")
        } else if (date.isNullOrEmpty()) {
            toast(view, "Date should not be empty...")
        } else if (email.isNullOrEmpty()) {
            toast(view, "Email should not be empty...")
        } else if (number.isNullOrEmpty()) {
            toast(view, "Phone no should not be empty...")
        } else if (address.isNullOrEmpty()) {
            toast(view, "Address should not be empty...")
        } else {
            viewModelScope.launch {
                var user = User(name = name!!,
                    email = email!!,
                    salary = salary!!,
                    address = address!!,
                    department = selectItem!!,
                    joiningDate = date!!,
                    phoneNo = number!!)
                repository.saveUser(user)
                toast(view, "Recode Successfully Saved...")
            }

        }
    }

    //get response livedata in view using this method
    fun getResponse(): MutableLiveData<String> {
        return mutableLiveData
    }

    //common toast
    fun toast(view: View, string: String) {
        Toast.makeText(view.context, string, Toast.LENGTH_SHORT).show()
    }
}