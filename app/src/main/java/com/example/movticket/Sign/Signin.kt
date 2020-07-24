package com.example.movticket.Sign

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movticket.Home.HomeActivity
import com.example.movticket.R
import com.example.movticket.Sign.SignUp.SignUp
import com.example.movticket.utils.Prefences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sigin.*


class Signin : AppCompatActivity() {

    lateinit var iUsername: String
    lateinit var iPassword: String

    //inisiasi database
    lateinit var database: DatabaseReference
    lateinit var preferences: Prefences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)

        //isi variabel database
        database = FirebaseDatabase.getInstance().getReference("User")
        preferences = Prefences(this)

        preferences.setValue(
            "Onboarding",
            "1"
        ) //nandain bahwa sudah pernah ke halaman sign in, jadi nanti onboarding gausah liat lagi

        //ngecek apakah udah pernah login sebelumnya, kalau udah langsung ke home
        if (preferences.getValue("status").equals("1")) {
            finishAffinity()
            startActivity(Intent(this@Signin, HomeActivity::class.java))
        }

        btn_masuk.setOnClickListener {

            iUsername = et_usrnm.text.toString()
            iPassword = et_pass.text.toString()

            if (iUsername.equals("")) {
                et_usrnm.error =
                    "Silahkan masukan username anda" //ngasih muncul peringatan bahwa teksnya kosong
                et_usrnm.requestFocus() //biar fokus
            } else
                if (iPassword.equals("")) {
                    et_pass.error =
                        "Silahkan masukan password anda" //ngasih muncul peringatan bahwa teksnya kosong
                    et_pass.requestFocus() //biar fokus
                } else {
                    pushLogin(iUsername, iPassword)
                }
        }

        btn_buatakun.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun pushLogin(username: String, pass: String) {

        database.child(username).addValueEventListener(

            object : ValueEventListener {
                override fun onCancelled(databaseerror: DatabaseError) {
                    Toast.makeText(this@Signin, "Database Error", Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    //User adalah sebuah class untuk menyimpang nilai dari user dari database, karena didatabse isi class user ada nama, email dkk
                    var user = dataSnapshot.getValue(User::class.java)

                    if (user == null) {
                        Toast.makeText( this@Signin, "User tidak ditemukan", Toast.LENGTH_LONG).show()
                        //Length_long : toastnya muncul lama
                    } else {
                        if (user.password.equals(pass)) { //sukses

                            //mengambil data dari server, supaya nanti gak perlu login lagi
                            preferences.setValue("nama", user.nama.toString())
                            preferences.setValue("username", user.username.toString())
                            preferences.setValue("email", user.email.toString())
                            preferences.setValue("saldo", user.saldo.toString())
                            preferences.setValue("url", user.url.toString())
                            preferences.setValue("status", "1")


                            startActivity(Intent(this@Signin, HomeActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@Signin, "Password anda salah",
                                Toast.LENGTH_LONG
                            ).show()

                        }

                    }

                }
            })
    }
}