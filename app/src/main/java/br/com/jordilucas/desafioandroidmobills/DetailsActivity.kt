package br.com.jordilucas.desafioandroidmobills

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.jordilucas.desafioandroidmobills.databinding.ActivityReceitaBinding
import br.com.jordilucas.desafioandroidmobills.model.Receita
import br.com.jordilucas.desafioandroidmobills.viewModel.ReceitaDetailsViewModel
import org.parceler.Parcels

class DetailsActivity : BaseActivity() {

    private val viewMOdel: ReceitaDetailsViewModel by lazy {
        ViewModelProvider(this).get(ReceitaDetailsViewModel::class.java)
    }

    private val binding: ActivityReceitaBinding by lazy{
        DataBindingUtil.setContentView<ActivityReceitaBinding>(
            this, R.layout.activity_details
        )
    }

    override fun init() {
        binding.receita?.let { receita ->
            viewMOdel.getReceita(receita.id).observe(this, Observer {
                binding.receita = it
            })
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receita = Parcels.unwrap<Receita>(intent.getParcelableExtra(DETAILS))
        if(receita != null){
            binding.receita = receita
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.menu_edit_receita){
            binding?.receita?.let {
                ReceitaFormActivity.start(this, it)
            }
            return true
        }
                return super.onOptionsItemSelected(item)
    }

    companion object{
        private const val DETAILS = "details"
        fun start(context: Context, receita: Receita){
            context.startActivity(
                Intent(context, DetailsActivity::class.java).apply {
                    putExtra(DETAILS, Parcels.wrap(receita))
                }
            )
        }
    }

}