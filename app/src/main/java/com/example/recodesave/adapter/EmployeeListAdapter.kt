package com.example.recodesave.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recodesave.R
import com.example.recodesave.databinding.ListLayoutEmployeeBinding
import com.example.recodesave.roomdb.User
import com.example.recodesave.utils.RecyclerViewClickListener
import com.example.recodesave.view.EmployeeDetailsFragmentDirections
import com.example.recodesave.view.EmployeesListFragmentDirections

class EmployeeListAdapter(
    private var user: MutableList<User>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<EmployeeListAdapter.EmployeesListViewModel>() {

    override fun getItemCount() = user.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EmployeesListViewModel(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_layout_employee,
                parent,
                false
            )
        )

    fun update(position: Int){
        user.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,user.size)
    }

    override fun onBindViewHolder(holder: EmployeesListViewModel, position: Int) {
        holder.recyclerviewMovieBinding.user = user[position]
        holder.recyclerviewMovieBinding.buttonDeleteEmployee.setOnClickListener {
            listener.onRecyclerViewItemClick(
                holder.recyclerviewMovieBinding.buttonDeleteEmployee,
                position,
                user.get(position)
            )
            //
        }
        holder.recyclerviewMovieBinding.buttonEditEmployee.setOnClickListener {
             listener.onRecyclerViewItemClick(
                holder.recyclerviewMovieBinding.buttonEditEmployee,
                position,
                user.get(position))
        }
    }


    inner class EmployeesListViewModel(
        val recyclerviewMovieBinding: ListLayoutEmployeeBinding
    ) : RecyclerView.ViewHolder(recyclerviewMovieBinding.root)
}
