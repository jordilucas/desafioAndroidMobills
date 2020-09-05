package br.com.jordilucas.desafioandroidmobills

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jordilucas.desafioandroidmobills.adapter.ReceitaAdapter
import br.com.jordilucas.desafioandroidmobills.livedata.observeOnce
import br.com.jordilucas.desafioandroidmobills.model.Receita
import br.com.jordilucas.desafioandroidmobills.viewModel.ReceitaListViewModel
import kotlinx.android.synthetic.main.fragment_receita_list.*

class ReceitaListFragment: Fragment() {

    private val viewModel: ReceitaListViewModel by lazy{
        ViewModelProvider(this).get(ReceitaListViewModel::class.java)
    }

    fun init() {
        try{
            viewModel.getReceitas().observe(this, Observer { receitas ->
            updateList(receitas)
            })
        }catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_receita_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        init()
        fab.setOnClickListener { startActivity(Intent(context, ReceitaFormActivity::class.java)) }
    }

    fun updateList(receitas: List<Receita>){
        receitaList.layoutManager = LinearLayoutManager(context)
        receitaList.adapter = ReceitaAdapter(receitas){ receita ->
            DetailsActivity.start(context!!, receita)
        }
        attachSwipeToRecyclerView()
    }

    private fun attachSwipeToRecyclerView(){
        val swipe = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteReceitaFromPosition(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(receitaList)
    }

    fun deleteReceitaFromPosition(position: Int){
        val adapter = receitaList.adapter as ReceitaAdapter
        val receita = adapter.receitas[position]
        viewModel.remove(receita).observeOnce(Observer { success ->
            if(!success){
                Toast.makeText(context, "Erro ao deletar receita", Toast.LENGTH_SHORT).show()
            }
        })
    }

}