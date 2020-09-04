package com.example.movticket.Sign.SignUp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.movticket.R
import com.example.movticket.Sign.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    lateinit var sUsername:String
    lateinit var sPassword:String
    lateinit var sNama:String
    lateinit var sEmail:String

    //inisiasi firebase(3)
    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabaseReference : DatabaseReference
    lateinit var mFirebasInstancee : FirebaseDatabase

    var currentApiVersion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentApiVersion = Build.VERSION.SDK_INT
        val flags: Int = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = flags
            val decorView: View = window.decorView
            decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN === 0) {
                    decorView.systemUiVisibility = flags
                }
            }
        }

        setContentView(R.layout.activity_sign_up)


        //ambil data firebase(3, masih bingung sama detailnya)
        mFirebasInstancee = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebasInstancee.getReference("User") // ambil data di folder "User" pada Databse


        btn_daftar.setOnClickListener {
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sEmail = et_email.text.toString()
            sNama = et_nama.text.toString()

            emptyValidation(sUsername, sPassword, sNama, sEmail)

        }
    }

    // fungsi ngecek apakah datanya udah keisi
    private fun emptyValidation(username:String, pass:String, nama:String, email:String) {
        if (username.equals("")){
            et_username.error = "Silahkan isi Usernae anda"
            et_username.requestFocus()
        }else if (pass.equals("")){
            et_password.error = "Silahkan isi Password anda"
            et_password.requestFocus()
        }else if (nama.equals("")){
            et_nama.error = "Silahkan isi Nama anda"
            et_nama.requestFocus()
        }else if (email.equals("")){
            et_email.error = "Silahkan isi Email anda"
            et_email.requestFocus()
        }else{
            saveUser(username, pass, nama, email)
        }
    }

    // fungsi kalau datanya udah keisi
    private fun saveUser(username: String, pass: String, nama: String, email: String) {

        var user = User()
        user.username = username
        user.password = pass
        user.nama = nama
        user.email = email
        user.transactionCount = 0
        user.ticketCount = 0

        // apabila username diisi oleh user (tidak null)
        if (username != null){
            chekingUsername(username, user)
        }

    }

    private fun chekingUsername(username: String, data: User) {

        mDatabaseReference.child(username).addValueEventListener(

            object : ValueEventListener{

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@SignUp, ""+databaseError.message, Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var user = dataSnapshot.getValue(User::class.java)


                    var nama = username
                    //Apabila username sudah digunakan  oleh pengguna lain
                    if (user == null){
                        //belum pernah dibuat

                        startActivity(Intent(this@SignUp, SignUpPhoto::class.java)
                            .putExtra("nama", data.nama)
                            .putExtra("uname", data.username)
                            .putExtra("email", data.email)
                            .putExtra("pass", data.password)
                            .putExtra("saldo", data.saldo)

                        )
                    } else{
                        //User pernah dibuat
                        Toast.makeText(this@SignUp, "User sudah pernah dibuat", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }
}