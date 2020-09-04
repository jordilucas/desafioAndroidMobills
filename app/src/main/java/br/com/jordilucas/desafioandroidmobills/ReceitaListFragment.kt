package br.com.jordilucas.desafioandroidmobills

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_receita_list.*

class ReceitaListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_receita_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        fab.setOnClickListener { startActivity(Intent(context, ReceitaFormActivity::class.java)) }
    }
}