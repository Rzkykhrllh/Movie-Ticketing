package com.example.movticket.Sign.SignUp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movticket.Home.HomeActivity
import com.example.movticket.R
import com.example.movticket.utils.Prefences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import kotlinx.android.synthetic.main.activity_sign_up_photo.*
import java.util.*
import android.app.ProgressDialog
import android.util.Log
import com.example.movticket.Sign.User
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*

class SignUpPhoto : AppCompatActivity() {

    val REQUEST_IMAGE_PICTURE = 1
    var statusAdd : Boolean = false // status kalau udah cari gambar nanti true
    lateinit var filepath : Uri //buat nyimpan path

    lateinit var storage : FirebaseStorage
    lateinit var storageRefrence : StorageReference
    lateinit var preferences: Prefences
    var user =  User()

    var mDatabaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
    lateinit var uname : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo)

        preferences = Prefences(this)
        storage = FirebaseStorage.getInstance()
        storageRefrence = storage.getReference()


        tv_welcome.text = "Selamat Datang\n"+intent.getStringExtra("nama") //ambil data dari intent sebelumnya

        getData()

        btn_add.setOnClickListener{

            if (statusAdd){
                //kalau udah ada gambar lalu di klik
                statusAdd = false
                btn_upload.visibility = View.VISIBLE
                btn_add.setImageResource(R.drawable.ic_add)
                img_profil.setImageResource(R.drawable.user_pict)
            } else{
                //belum ada gambar dan tombol add di click

                Log.v("checkpoint : ","1")
                ImagePicker.with(this)
                    .galleryOnly()
                    .start()

                Log.v("checkpoint : ","2")
            }
        }

        btn_upload_nanti.setOnClickListener {

            setPreferences()
            user.url = ""
            mDatabaseReference.child(user.username!!).setValue(user)

            finishAffinity()
            startActivity(Intent(this@SignUpPhoto, HomeActivity::class.java))
        }

        btn_upload.setOnClickListener {
            if (filepath != null){

                // menampilkan bahwa kita sedang mengupload sesuatu
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()


                //firebase
                var ref  = storageRefrence.child("Images/"+UUID.randomUUID().toString()) //biar gak bentrok?

                ref.putFile(filepath) //upload file gambar yang ada di filepath
                    .addOnSuccessListener { //action yang bakal dilakukan kalau upload sukses
                        progressDialog.dismiss() //menghilangkan progresssbar
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show() //memberi tahu bahwa upload sukses


                        //gak ngerti ngapain kode dibawah ini
                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValue("url", it.toString())
                            user.url = it.toString()
                        }

                        //pindah ke home
                        preferences.setValue("status", "1")

                        setPreferences()
                        mDatabaseReference.child(user.username!!).setValue(user)

                        finishAffinity()
                        startActivity(Intent(this@SignUpPhoto, HomeActivity::class.java))
                    }
                    .addOnCanceledListener { //action yang dilakukan kalau gagal upload
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {//menampilkan berapa persen yang sudah terupload
                        taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uplaod "+progress.toInt()+" %")
                    }

            }else{

            }
        }
    }

    private fun getData() {
        user.nama = intent.getStringExtra("nama")
        user.username = intent.getStringExtra("uname")
        user.email = intent.getStringExtra("email")
        user.saldo = intent.getStringExtra("saldo")
        user.password = intent.getStringExtra("pass")

    }

    private fun setPreferences() {


        preferences.setValue("nama", user.nama.toString())
        preferences.setValue("username", user.username.toString())
        preferences.setValue("email", user.email.toString())
        preferences.setValue("saldo", user.saldo.toString())
        preferences.setValue("status", "1")
        preferences.setValue("transactionCount","0")
        preferences.setValue("ticketCount","0")
    }

     fun onPermissionGranted(response: PermissionGrantedResponse?) {
        //kalau disetujuin (apa yang disetujuin ?)
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_PICTURE)
            }
        }
    }

     fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        // gausah diisi
    }

     fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan foto profile", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah ? Klik tombol upload nanti aja ", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){

            statusAdd =  true
            filepath = data?.data!!

            //memnculkan foto yang dipilih
            Glide.with(this)
                .load(filepath)
                .apply(RequestOptions.circleCropTransform()) //membuat gambar menjadi lingkaran
                .into(img_profil)

            Log.v("asu", "file uri uoload : "+filepath)

            btn_upload.visibility = View.VISIBLE //menampilkan tombol "Simpan dan lanjutkan"
            btn_add.setImageResource(R.drawable.ic_delete)

        } else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(this, "req code : $requestCode \nresultocde = $resultCode", Toast.LENGTH_LONG).show()
            //Toast.makeText(this, "task cancelled", Toast.LENGTH_LONG).show()
        }

    }
}