package br.com.jordilucas.desafioandroidmobills.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.jordilucas.desafioandroidmobills.firebase.FbReceitaRepository
import br.com.jordilucas.desafioandroidmobills.model.Receita

class ReceitaDetailsViewModel: ViewModel() {

    private val repo = FbReceitaRepository()
    private val selectReceitaId = MutableLiveData<String>()

    private var selectReceita = Transformations.switchMap(selectReceitaId){receitaId ->
        repo.loadReceita(receitaId)
    }

    fun getReceita(id: String) : LiveData<Receita> {
        selectReceitaId.value = id
        return selectReceita
    }

}