package de.grademanager.core.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grademanager.core.presentation.snackbar.SnackbarController
import kotlinx.coroutines.launch

class AppHostViewModel(
    private val snackbarController: SnackbarController
) : ViewModel() {

    val errorSnackbarData = snackbarController.errorSnackbar
    val neutralSnackbarData = snackbarController.neutralSnackbar

    fun clearErrorSnackbarData() {
        viewModelScope.launch {
            snackbarController.clearErrorSnackbarData()
        }
    }

    fun clearNeutralSnackbarData() {
        viewModelScope.launch {
            snackbarController.clearNeutralSnackbarData()
        }
    }

}