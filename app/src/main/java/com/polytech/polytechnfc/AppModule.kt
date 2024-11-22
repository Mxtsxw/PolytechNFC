package com.polytech.polytechnfc

import com.polytech.polytechnfc.ViewModel.SignInViewModel
import com.polytech.polytechnfc.ViewModel.SignOutViewModel
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import com.polytech.polytechnfc.model.service.module.AccountServiceImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.polytech.polytechnfc.ViewModel.RecordsViewModel

val appModule = module {
    single { AccountServiceImpl() }
    single { FirestoreServiceImpl() }
    viewModel { SignInViewModel(get()) }
    viewModel { SignOutViewModel(get()) }
    viewModel { RecordsViewModel(get()) }
}