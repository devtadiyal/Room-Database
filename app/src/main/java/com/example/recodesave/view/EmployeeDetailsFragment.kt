package com.example.recodesave.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recodesave.R
import com.example.recodesave.databinding.EmployeeDetailsFragmentBinding
import com.example.recodesave.viewmodel.EmployeeDetailsViewModel
import com.example.recodesave.viewmodel.EmployeeViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class EmployeeDetailsFragment : Fragment() , KodeinAware {

    override val kodein by kodein()
    private val factory: EmployeeViewModelFactory by instance()
    private lateinit var viewModel: EmployeeDetailsViewModel
    private lateinit var binding: EmployeeDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.employee_details_fragment, container, false)
        viewModel = ViewModelProvider(this,factory).get(EmployeeDetailsViewModel::class.java)
        binding.viewModel = viewModel//attach your viewModel to xml

        //to get save button response
        viewModel.getResponse().observe(viewLifecycleOwner, Observer {
            str -> binding.editTextDate.setText(str)
        })


        return binding.root
    }


}