package br.com.jordilucas.desafioandroidmobills.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.jordilucas.desafioandroidmobills.firebase.FbReceitaRepository
import br.com.jordilucas.desafioandroidmobills.model.Receita


class ReceitaFormViewModel: ViewModel() {

    private val repo = FbReceitaRepository()
    var receita: Receita? = null

    private var isSelect = MutableLiveData<Boolean>().apply { value = false }
    private var showProgress = MutableLiveData<Boolean>().apply { value = false }
    private var saveReceita = MutableLiveData<Receita>()
    private var savingReceitaOperation = Transformations.switchMap(saveReceita){ receita ->
        showProgress.value = true
        Transformations.map(repo.saveReceita(receita)){ success ->
            showProgress.value = false
            success
        }
    }

    fun showProgress():LiveData<Boolean> = showProgress
    fun savingOperation(): LiveData<Boolean> = savingReceitaOperation

    fun saveReceita(receita: Receita){
        saveReceita.value = receita
    }


    fun checkedValue(){
        if(true){
            receita?.recebido = true
        }else{
            receita?.recebido = false
        }
    }

}