package com.example.latihanroom2

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanroom2.adapter.UserAdater
import com.example.latihanroom2.entities.UserDao
import com.example.latihanroom2.entities.UserEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.*
import io.reactivex.schedulers.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_dialog.view.*

/*29 April 2020
Nama: Muiz Ahsanu Haqi
Nim : 10117199
Kelas: IF-5
 */

class MainActivity : AppCompatActivity() {

    private var db: UserDatabase? = null
    private val disposable = CompositeDisposable()

    private lateinit var users: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = UserDatabase.getIntance(this)
        rv_user_main.layoutManager = LinearLayoutManager(this)
        rv_userByZona_main.layoutManager = LinearLayoutManager(this)

        btn_tambah_main.setOnClickListener(){
            val dialogBuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.input_dialog,null)
            dialogBuilder.setView(view)
            dialogBuilder.setTitle("Masukkan data baru")
            dialogBuilder.setPositiveButton("Tambah"){ _:DialogInterface,_: Int->
                val nama = view.et_nama_dialog.text.toString()
                val email = view.et_email_dialog.text.toString()
                val zona = view.et_zona_dialog.text.toString()

                users = UserEntity(0,nama,email,zona)
                insertData(users)
            }
            dialogBuilder.setNegativeButton("Batal"){ _:DialogInterface,_: Int->

            }
            dialogBuilder.show()
        }
        btn_cari_main.setOnClickListener(){
            val cariZona = et_cari_main.text.toString()
            getDataUserByZona(cariZona)
        }

        btn_delete_main.setOnClickListener(){
            deleteUsers()
        }
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    fun insertData(users: UserEntity){
        disposable.add(Observable.fromCallable { db?.UserDao()?.tambahData(users)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                getData()})
    }

    fun getData(){
        disposable.add(Observable.fromCallable { db?.UserDao()?.getDataUser()}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                rv_user_main.adapter = UserAdater(it!!,this)
            })
    }
    fun deleteUsers(){
        disposable.add(Observable.fromCallable { db?.UserDao()?.deleteAllUsers()}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                getData()
            })
    }
    fun getDataUserByZona(zona:String){
        disposable.add(Observable.fromCallable { db?.UserDao()?.getDataUserByZona(zona)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                rv_userByZona_main.adapter = UserAdater(it!!,this)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        UserDatabase.destroyIntances()
        disposable.dispose()
    }

}
