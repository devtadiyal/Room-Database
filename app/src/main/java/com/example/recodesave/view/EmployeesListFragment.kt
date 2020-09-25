package com.example.recodesave.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recodesave.R
import com.example.recodesave.adapter.EmployeeListAdapter
import com.example.recodesave.databinding.EmployeesListFragmentBinding
import com.example.recodesave.roomdb.AppDatabase
import com.example.recodesave.roomdb.User
import com.example.recodesave.utils.RecyclerViewClickListener
import com.example.recodesave.viewmodel.EmployeeListModelFactory
import com.example.recodesave.viewmodel.EmployeesListViewModel
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.String

class EmployeesListFragment : Fragment(), KodeinAware, RecyclerViewClickListener {
    override val kodein by kodein()
    private val factory: EmployeeListModelFactory by instance()
    private lateinit var viewModel: EmployeesListViewModel
    private lateinit var binding: EmployeesListFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:EmployeeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.employees_list_fragment, container, false)
        viewModel = ViewModelProvider(this, factory).get(EmployeesListViewModel::class.java)
        binding.viewModel = viewModel//attach your viewModel to xml

        viewModel.getUsers()
        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            linearLayoutManager = LinearLayoutManager(context)
            binding.recyclerViewMovies.layoutManager = linearLayoutManager
            adapter = EmployeeListAdapter(users, this)
            binding.recyclerViewMovies.adapter = adapter
        })

        return binding.root
    }

    override fun onRecyclerViewItemClick(view: View, position: Int, user: User) {
        when (view.id) {
            R.id.buttonDeleteEmployee -> {

                val alertbox = AlertDialog.Builder(requireActivity())
                alertbox.setTitle("Delete Recode")
                alertbox.setMessage("Do you want to delete employee recode?")
                alertbox.setPositiveButton(
                    "Yes"
                ) { arg0, arg1 ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        AppDatabase(requireContext()).getUserDao().deleteUser(user)
                        adapter.delete(position)
                    }
                }
                alertbox.setNegativeButton(
                    "No"
                ) { arg0, arg1 -> }
                alertbox.show()

            }
            R.id.buttonEditEmployee -> {
                updateEmployee(user,position)
            }
        }
    }
    private fun updateEmployee(user: User,position: Int) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.dialog_update_employee, null)
        builder.setView(view)
        val editTextName = view.findViewById<EditText>(R.id.editName)
        val editTextEmail = view.findViewById<EditText>(R.id.editEmail)
        val editTextMobile = view.findViewById<EditText>(R.id.editMobile)
        val editTextAddress = view.findViewById<EditText>(R.id.Address)
        val editTextDate = view.findViewById<EditText>(R.id.editDate)
        val editTextSalary = view.findViewById<EditText>(R.id.editSalary)
        val spinnerDepartment = view.findViewById<Spinner>(R.id.spinDepartment)
        editTextName.setText(user.name)
        editTextEmail.setText(user.email)
        editTextMobile.setText(user.phoneNo)
        editTextAddress.setText(user.address)
        editTextDate.setText(user.joiningDate)
        editTextSalary.setText(user.salary)
        spinnerDepartment.setSelection(position)
        val dialog = builder.create()
        dialog.show()
        view.findViewById<View>(R.id.UpdateEmployee).setOnClickListener(View.OnClickListener {
            val name = editTextName.text.toString().trim { it <= ' ' }
            val email = editTextEmail.text.toString().trim { it <= ' ' }
            val mobile = editTextMobile.text.toString().trim { it <= ' ' }
            val address = editTextAddress.text.toString().trim { it <= ' ' }
            val date = editTextDate.text.toString().trim { it <= ' ' }
            val salary = editTextSalary.text.toString().trim { it <= ' ' }
            val dept = spinnerDepartment.selectedItem.toString()

            var mUser = User(
                name = name!!,
            email = email!!,
            salary = salary!!,
            address = address!!,
            department = dept!!,
            joiningDate = date!!,
            phoneNo = mobile!!)

            if (name.isEmpty()) {
                editTextName.error = "Name can't be blank"
                editTextName.requestFocus()
                return@OnClickListener
            }
            if (email.isEmpty()) {
                editTextEmail.error = "Email can't be blank"
                editTextEmail.requestFocus()
                return@OnClickListener
            }
            if (mobile.isEmpty()) {
                editTextMobile.error = "Mobile No can't be blank"
                editTextMobile.requestFocus()
                return@OnClickListener
            }
            if (address.isEmpty()) {
                editTextAddress.error = "Address can't be blank"
                editTextAddress.requestFocus()
                return@OnClickListener
            }
            if (date.isEmpty()) {
                editTextDate.error = "Date can't be blank"
                editTextDate.requestFocus()
                return@OnClickListener
            }
            if (salary.isEmpty()) {
                editTextSalary.error = "Salary can't be blank"
                editTextSalary.requestFocus()
                return@OnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                 mUser.id = user.id
                AppDatabase(requireContext()).getUserDao().updateUser(mUser)
                Toast.makeText(context,"Data Updated...",Toast.LENGTH_SHORT).show()
                adapter.update(position,mUser)
            }
            dialog.dismiss()
        })
    }

}