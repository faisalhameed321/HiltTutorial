package com.example.dependecy_injection.utils

import com.example.dependecy_injection.data.model.StatusModel


sealed class UIState {
    object Loading : UIState()
    class Success(val statusList: MutableList<StatusModel>) : UIState()

}