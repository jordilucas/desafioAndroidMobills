package br.com.jordilucas.desafioandroidmobills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.signin_activity.*

class SigninActivity : AppCompatActivity() {

    private var googleApiClient: GoogleApiClient? = null
    private var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_activity)
        initGoogleSignin()
        btnSignin.setOnClickListener { signIn() }
    }

    private fun initGoogleSignin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this){
                showErrorMessage()
            }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun signIn(){
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_GOOGLE_SIGNIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_GOOGLE_SIGNIN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result!!.isSuccess){
                val account  = result.signInAccount
                if(account != null){
                    firebaseAuthWithGoogle(account)
                }else{
                    showErrorMessage()
                }
            }else{
                showErrorMessage()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acc: GoogleSignInAccount){
        val credencial = GoogleAuthProvider.getCredential(acc.idToken, null)
        fbAuth.signInWithCredential(credencial)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, BaseActivity::class.java))
                }
                else{
                    showErrorMessage()
                }
            }
    }

    fun showErrorMessage(){
        Toast.makeText(this, R.string.error_google_signin, Toast.LENGTH_LONG).show()
    }

    companion object{
        const val RC_GOOGLE_SIGNIN = 1
    }

}