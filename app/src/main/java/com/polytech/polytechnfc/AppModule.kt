package com.polytech.polytechnfc

import android.os.Build
import androidx.annotation.RequiresApi
import com.polytech.polytechnfc.ViewModel.AccessViewModel
import com.polytech.polytechnfc.ViewModel.CardsViewModel
import com.polytech.polytechnfc.ViewModel.ReadersViewModel
import com.polytech.polytechnfc.ViewModel.SignInViewModel
import com.polytech.polytechnfc.ViewModel.SignOutViewModel
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import com.polytech.polytechnfc.model.service.module.AccountServiceImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.polytech.polytechnfc.ViewModel.RecordsViewModel
import com.polytech.polytechnfc.ViewModel.RolesViewModel
import com.polytech.polytechnfc.ViewModel.RoomsListViewModel

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {
    single { AccountServiceImpl() }
    single { FirestoreServiceImpl() }
    viewModel { SignInViewModel(get()) }
    viewModel { SignOutViewModel(get()) }
    viewModel { RecordsViewModel(get()) }
    viewModel { CardsViewModel(get()) }
    viewModel { RoomsListViewModel(get()) }
    viewModel { RolesViewModel(get()) }
    viewModel { AccessViewModel(get()) }
    viewModel { ReadersViewModel(get()) }
}