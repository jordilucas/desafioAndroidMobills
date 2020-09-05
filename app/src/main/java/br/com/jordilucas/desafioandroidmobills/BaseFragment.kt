package br.com.jordilucas.desafioandroidmobills

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide.init
import com.google.firebase.auth.FirebaseAuth

abstract  class BaseFragment: Fragment() {

    protected val fbAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var hasInitCalled = false

    private var authListener: FirebaseAuth.AuthStateListener =
        FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if(user != null){
                if(!hasInitCalled){
                    hasInitCalled = true
                    init()
                }
            }else{
                val it = Intent(context, SigninActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
                startActivity(it)
            }
        }

    override fun onStart() {
        super.onStart()
        fbAuth.addAuthStateListener { authListener }
    }

    override fun onStop() {
        super.onStop()
        fbAuth.removeAuthStateListener { authListener }
    }

    protected abstract fun init()


}