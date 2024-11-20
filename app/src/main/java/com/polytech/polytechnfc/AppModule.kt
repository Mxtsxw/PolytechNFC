package com.polytech.polytechnfc

import com.polytech.polytechnfc.ViewModel.SignInViewModel
import com.polytech.polytechnfc.ViewModel.SignOutViewModel
import com.polytech.polytechnfc.model.service.module.AccountServiceImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AccountServiceImpl() }
    viewModel { SignInViewModel(get()) }
    viewModel { SignOutViewModel(get()) }
}