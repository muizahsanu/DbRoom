package com.example.latihanroom2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanroom2.adapter.UserAdater
import com.example.latihanroom2.entities.UserDao
import com.example.latihanroom2.entities.UserEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.*
import io.reactivex.schedulers.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var db: UserDatabase? = null
    private val disposable = CompositeDisposable()

    private lateinit var users: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = UserDatabase.getIntance(this)
        rv_user_main.layoutManager = LinearLayoutManager(this)

        btn_tambah_main.setOnClickListener(){
            val nama = et_nama_main.text.toString()
            val umur = et_umur_main.text.toString()
            users = UserEntity(0,nama,umur)
            insertData(users)
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

    override fun onDestroy() {
        super.onDestroy()
        UserDatabase.destroyIntances()
        disposable.dispose()
    }

}
