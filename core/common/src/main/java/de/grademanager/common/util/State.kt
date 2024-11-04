package de.grademanager.common.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update

class State <T>(initial: T) {

    private val _state = MutableStateFlow(initial)
    val state = _state.asStateFlow()

    fun update(other: T) {
        _state.update { other }
    }

    fun update(block: (T) -> T) {
        _state.update(block)
    }

    suspend fun collectLatest(action: suspend (value: T) -> Unit) {
        _state.collectLatest(action)
    }

    fun currentValue(): T {
        return _state.value
    }

}