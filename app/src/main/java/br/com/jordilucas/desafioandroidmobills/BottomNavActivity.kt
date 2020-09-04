package br.com.jordilucas.desafioandroidmobills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        bottomNav.setOnNavigationItemSelectedListener(this)
        newFragment(HomeFragment())
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home_nav ->{
                newFragment(HomeFragment())
                return true
            }
            R.id.receita_nav ->{
                newFragment(ReceitaFragment())
                return true
            }
            R.id.despesa_nav ->{
                newFragment(DespesaFragment())
                return true
            }
            R.id.conta_nav ->{
                newFragment(MinhaContaFragment())
                return true
            }
        }
        return false
    }

    fun newFragment(container: Fragment){
        val fragment = container
        supportFragmentManager.beginTransaction().replace(R.id.container, container, fragment.javaClass.simpleName)
            .commit()
    }

}