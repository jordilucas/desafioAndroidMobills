package br.com.jordilucas.desafioandroidmobills.firebase

import androidx.lifecycle.LiveData
import br.com.jordilucas.desafioandroidmobills.model.Receita
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FbReceitaRepository {
    private val fbAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = fbAuth.currentUser

    fun saveReceita(receita:Receita): LiveData<Boolean>{
        return object: LiveData<Boolean>(){
            override fun onActive() {
                super.onActive()
                if(currentUser == null){
                    throw SecurityException("Usuario invalido")
                }

                val db = firestore
                val collection = db.collection(RECEITA_KEY)
                val newReceita = if(receita.id.isBlank()){
                    receita.userId = currentUser.uid
                    collection.add(receita).continueWith { task ->
                        if(task.isSuccessful){
                            receita.id = task.result?.id ?:""
                        }
                    }
                }else{
                    collection.document(receita.id).set(receita, SetOptions.merge())
                }
                newReceita.addOnCompleteListener {
                    value = true
                }.addOnFailureListener {
                    value = false
                }
            }
        }
    }

    fun loadReceitas(): LiveData<List<Receita>>{
        return object : LiveData<List<Receita>>(){
            override fun onActive() {
                super.onActive()

                firestore.collection(RECEITA_KEY)
                    .whereEqualTo(USER_ID, currentUser?.uid)
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        if(firebaseFirestoreException == null){
                            val receitas = querySnapshot?.map { document ->
                                val receita = document.toObject(Receita::class.java)
                                receita.id = document.id
                                receita
                            }
                            value = receitas
                        }else{
                            throw firebaseFirestoreException
                        }
                    }

            }
        }
    }

    fun loadReceita(receitaId: String): LiveData<Receita>{
        return object : LiveData<Receita>(){
            override fun onActive() {
                super.onActive()
                firestore.collection(RECEITA_KEY)
                    .document(receitaId)
                    .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                        if(firebaseFirestoreException == null){
                            if(documentSnapshot != null){
                                val receita = documentSnapshot.toObject(Receita::class.java)
                                receita?.id = documentSnapshot.id
                                value = receita
                            }
                        }else{
                            throw firebaseFirestoreException
                        }
                    }
            }
        }
    }

    fun removeReceita(receita: Receita): LiveData<Boolean>{
        return object : LiveData<Boolean>(){
            override fun onActive() {
                super.onActive()
                val db = firestore
                db.collection(RECEITA_KEY)
                    .document(receita.id)
                    .delete()
                    .addOnCompleteListener {
                        value = it.isSuccessful
                    }
            }
        }
    }

    companion object{
        const val RECEITA_KEY = "receita"
        const val USER_ID = "userId"
        const val DESPESA_KEY = "despesa"
    }



}