package com.demo.ui.preferences.app

import androidx.lifecycle.LiveData
import com.demo.base.BaseViewModel
import com.demo.common.livedata.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainPreferencesViewModel @Inject constructor() : BaseViewModel<MainPreferencesViewModel.ActionType>() {

    private val viewData = LiveEvent<Data>()

    fun observeViewData(): LiveData<Data> = viewData


    sealed class ActionType

    sealed class Data
}