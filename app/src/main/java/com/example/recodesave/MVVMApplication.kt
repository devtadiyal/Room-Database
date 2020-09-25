package com.example.recodesave

import android.app.Application
import com.example.recodesave.repository.UserRepository
import com.example.recodesave.roomdb.AppDatabase
import com.example.recodesave.viewmodel.EmployeeListModelFactory
import com.example.recodesave.viewmodel.EmployeeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance()) }
        //no need of single object so use provider
        bind() from provider { EmployeeViewModelFactory(instance()) }
        bind() from provider { EmployeeListModelFactory(instance()) }
    }

}