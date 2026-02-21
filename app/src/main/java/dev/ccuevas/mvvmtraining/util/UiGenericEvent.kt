package dev.ccuevas.mvvmtraining.util

sealed class UiGenericEvent {

    object PopBackStack : UiGenericEvent()

    data class Navigate(val route: String) : UiGenericEvent()

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UiGenericEvent()

}