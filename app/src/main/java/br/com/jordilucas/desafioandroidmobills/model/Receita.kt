package br.com.jordilucas.desafioandroidmobills.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import br.com.jordilucas.desafioandroidmobills.BR
import org.parceler.Parcel
import java.util.*

@Parcel
class Receita: BaseObservable(){
    @Bindable
    var id: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }
    @Bindable
    var valorReceita:Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.valorReceita)
        }
    @Bindable
    var descricao:String=""
        set(value) {
            field = value
            notifyPropertyChanged(BR.descricao)
        }
    @Bindable
   public var dataReceita:Int =0
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataReceita)
        }
    @Bindable
    var recebido:Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.recebido)
        }

    var userId:String = ""
}