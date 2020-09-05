package br.com.jordilucas.desafioandroidmobills.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.jordilucas.desafioandroidmobills.firebase.FbReceitaRepository
import br.com.jordilucas.desafioandroidmobills.model.Receita

class ReceitaListViewModel: ViewModel() {

    private val repo = FbReceitaRepository()
    private var receitaList: LiveData<List<Receita>>?=null

    fun remove(receita: Receita) = repo.removeReceita(receita)

    fun getReceitas(): LiveData<List<Receita>>{
        var list = receitaList
        if(list == null){
            list = repo.loadReceitas()
            receitaList = list
        }
        return list
    }

}