package br.com.jordilucas.desafioandroidmobills.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.jordilucas.desafioandroidmobills.R
import br.com.jordilucas.desafioandroidmobills.databinding.ItemReceitaBinding
import br.com.jordilucas.desafioandroidmobills.model.Receita

class ReceitaAdapter(val receitas: List<Receita>, private val onCLick:(Receita) -> Unit): RecyclerView.Adapter<ReceitaAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receita, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.run {
            val currentReceita = receitas[position]
            receita = currentReceita
            root.setOnClickListener {
                onCLick(currentReceita)
            }
            executePendingBindings()
        }
    }

    override fun getItemCount() = receitas.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = DataBindingUtil.bind<ItemReceitaBinding>(view)
    }

}