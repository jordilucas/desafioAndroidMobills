package br.com.jordilucas.desafioandroidmobills

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.com.jordilucas.desafioandroidmobills.databinding.ActivityFormReceitaBinding
import br.com.jordilucas.desafioandroidmobills.model.Receita
import br.com.jordilucas.desafioandroidmobills.viewModel.ReceitaFormViewModel
import kotlinx.android.synthetic.main.form_receita.*
import org.parceler.Parcels
import java.text.SimpleDateFormat
import java.util.*

class ReceitaFormActivity : BaseActivity() {

    private val viewModel: ReceitaFormViewModel by lazy{
        ViewModelProvider(this).get(ReceitaFormViewModel::class.java)
    }

    private val binding: ActivityFormReceitaBinding by lazy{
        DataBindingUtil.setContentView(this, R.layout.activity_form_receita)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.content.receita = if(savedInstanceState == null){
            Parcels.unwrap<Receita>(intent.getParcelableExtra(RECEITA))?:Receita()
        }else{
            Parcels.unwrap<Receita>(savedInstanceState.getParcelable(RECEITA))
        }

        binding.content.view = this

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun init() {
        viewModel.showProgress().observe(this, Observer { loading ->
            loading?.let {
                btnSalvar.isEnabled = !loading
                binding.content.progressBar.visibility = if(it)View.VISIBLE else View.GONE
            }
        })
        viewModel.savingOperation().observe(this, Observer { success ->
            success?.let {
                if(success){
                    showMessageSuccess()
                    finish()
                }else{
                    showMessageError()
                }
            }
        })
    }

    private fun showMessageSuccess(){
        Toast.makeText(this, R.string.receita_salvo, Toast.LENGTH_SHORT).show()
    }

    private fun showMessageError(){
        Toast.makeText(this, R.string.receita_error, Toast.LENGTH_SHORT).show()
    }

    fun clickSave(view: View){
        val receita = binding.content.receita
        receita?.recebido = binding.content.ckbRecebido.isChecked
        if(receita != null){
            try {
                viewModel.saveReceita(receita)
            }catch (e:Exception){
                showMessageError()
            }
        }
    }

    fun setDate(view: View){
        var calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatterDate = formatter.format(calendar)
        val teste = calendar.time.toString()
        edtDate.editText?.setText(teste)// = teste //binding.content.edtDate.editText?.setText(teste)
    }

    companion object{
        const val RECEITA ="receita"

        fun start(context: Context, receita:Receita){
            context.startActivity(Intent(context, ReceitaFormActivity::class.java).apply {
                putExtra(RECEITA, Parcels.wrap(receita))
            })
        }

    }




}