package com.example.secondapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.secondapp.objects.Item
import com.example.secondapp.datalayer.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val cats = MutableLiveData<List<Item>>()

    var isLoading = MutableLiveData(false)
    var isError = MutableLiveData(false)
    var compositeDisposable = CompositeDisposable()

    init {
        loadCats()
    }

    fun loadCats(){
        val loading = isLoading.value
        if (loading!=null && loading==true)
            return

        val disposable = ApiFactory.apiService.loadCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doAfterTerminate { isLoading.value = false }
            .subscribe(
                { listCats ->
                    isError.value = false
                    val loadedCats = ArrayList<Item>(1)
                    cats.value?.let { loadedCats.addAll(it) }

                    if(loadedCats.isNotEmpty()) {
                        loadedCats.addAll(listCats)
                        cats.value = loadedCats
                    } else
                        cats.value = listCats
                }
            ) { isError.value = true }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
