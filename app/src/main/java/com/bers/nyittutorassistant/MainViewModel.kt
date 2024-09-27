package com.bers.nyittutorassistant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository = Repository()
    val viewState = MutableLiveData<ViewState>()
    var questionDataList: QuestionDataList? = null

    fun getQuestionData(tutorName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getQuestionData(tutorName)) {
                is Response.Success<QuestionDataList> -> {
                    viewState.postValue(ViewState.SUCCESS)
                    questionDataList = result.data
                }
                else -> viewState.postValue(ViewState.FAILURE)
            }
        }
    }

    fun updateQuestionData(questionData: QuestionData) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.updateQuestionData(questionData)) {
                is Response.Success -> {
                    viewState.postValue(ViewState.SUCCESS)
                }
                else -> viewState.postValue(ViewState.FAILURE)
            }
        }
    }

    enum class ViewState {
        SUCCESS,
        FAILURE,
        LOADING,
    }
}