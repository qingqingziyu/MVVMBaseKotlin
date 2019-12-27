package com.cf.mvvmbasekotlin.di

import com.cf.mvvmbasekotlin.model.respository.MainRespository
import com.cf.mvvmbasekotlin.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val respositoryModule = module {
    single { MainRespository() }
}

val appModule = listOf(viewModelModule, respositoryModule)