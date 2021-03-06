package com.example.digitron.navigationComponent

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toIcon
import com.example.digitron.R
import com.example.digitron.databinding.ActivityAccountDetailsBinding
import com.example.digitron.databinding.DeletePromptBinding
import com.example.digitron.databinding.FilterBottomSheetBinding
import com.example.digitron.databinding.NavHeaderLayoutBinding
import com.example.digitron.userDatabase.UserDao
import com.example.digitron.userentrance.ForgotPassword
import com.example.digitron.userentrance.SignIn
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.text.DateFormat
import java.text.SimpleDateFormat

class AccountDetails : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAccountDetailsBinding

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        val view= binding.root
        supportActionBar?.title= "Accounts"
        window.statusBarColor = ContextCompat.getColor(this, R.color.red)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.red)))
        setContentView(view)

        val pref = getSharedPreferences("user_details", MODE_PRIVATE)
        val editor = pref.edit()

            Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.displayName.toString())
                .get().addOnSuccessListener {
                    if (it != null) {
                        GlobalScope.launch(Dispatchers.IO) {
                            val name = it.getString("name")
                            val email = it.getString("email")
                            val userImage = it.getString("name")?.get(0)?.toUpperCase().toString()

                            editor.putString("name",name)
                            editor.putString("email",email)
                            editor.putString("image",userImage)
                            editor.apply()
                        }
                    } else {
                        editor.clear().apply()
                        Toast.makeText(this@AccountDetails, "Can't fetch data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

        binding.userImage.text = pref.getString("image","U")
        binding.email.setText(pref.getString("email","user@email.com"))
        binding.name.setText(pref.getString("name","User Name"))
        binding.changePassword.setOnClickListener(this)
        binding.delete.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.changePassword -> {
                startActivity(Intent(this,ForgotPassword::class.java))
            }
            R.id.delete -> {
                val bottomSheet = BottomSheetDialog(this)
                val bind = DeletePromptBinding.inflate(layoutInflater)
                bottomSheet.setContentView(bind.root)
                bottomSheet.setCancelable(false)
                bottomSheet.show()

                bind.yesDelete.setOnClickListener{
                    val current = Firebase.auth.currentUser
                    current?.delete()
                    Firebase.auth.signOut()
                    startActivity(Intent(this,SignIn::class.java))
                    bottomSheet.dismiss()
                }
                bind.no.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }

        }
    }
}