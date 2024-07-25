package com.example.disasterproject

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.disasterproject.interfacemy.SignOutInterface

class MainActivity : AppCompatActivity(),SignOutInterface {
//    this is the refernce of the widgets
    lateinit var editFname:EditText
    lateinit var editLname:EditText
    lateinit var editEmail:EditText
    lateinit var editContact:EditText
    lateinit var nextBtn:Button
    lateinit var fName :String
    lateinit var lName :String
    lateinit var email :String
    lateinit var contact :String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setting the duration if the splash screen
        Thread.sleep(3000)
//        callinf the method of installsplashscreen
        installSplashScreen()
        setContentView(R.layout.activity_main)

//        initilizing all the references
        initrefernce()
        var token= getSharedPreferences("username", Context.MODE_PRIVATE)
        if(token.getString("loginusername"," ")!=" "){
                var intent = Intent(this,SignInActivity::class.java)
                startActivity(intent)
                finish()
        }

        addTextWatchers()
//        calling the nextbutton
        nextBtn.setOnClickListener {
            fName=editFname.text.toString()
            lName=editLname.text.toString()
            email=editEmail.text.toString()
            contact=editContact.text.toString()
            if(fName.isEmpty()){
                Toast.makeText(MainActivity@this,"Fill First Name",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(lName.isEmpty()){
                Toast.makeText(MainActivity@this,"Fill Last Name",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(email.isEmpty()){
                Toast.makeText(MainActivity@this,"Fill Email Address",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(contact.isEmpty()){
                Toast.makeText(MainActivity@this,"Fill Contact",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            var intent =Intent(MainActivity@this,SignInActivity::class.java)
            intent.putExtra("username",fName)


            var editor= token.edit()
            editor.putString("loginusername",fName)
            editor.commit()
            startActivity(intent)



        }
    }


    fun initrefernce(){
//        initializing the refernces
        editFname=findViewById(R.id.editTextUserName)
        editLname=findViewById(R.id.editTextLname)
        editEmail=findViewById(R.id.editTextTextEmailAddress)
        editContact=findViewById(R.id.editTextPhone)
        nextBtn=findViewById(R.id.buttonNext)
    }

    private fun addTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldsForEmptyValues()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        editFname.addTextChangedListener(textWatcher)
        editLname.addTextChangedListener(textWatcher)
        editEmail.addTextChangedListener(textWatcher)
        editContact.addTextChangedListener(textWatcher)
    }

    private fun checkFieldsForEmptyValues() {
        fName = editFname.text.toString()
        lName = editLname.text.toString()
        email = editEmail.text.toString()
        contact = editContact.text.toString()

        nextBtn.isEnabled = fName.isNotEmpty() && lName.isNotEmpty() && email.isNotEmpty() && contact.isNotEmpty()

        if (nextBtn.isEnabled) {
            nextBtn.setBackgroundColor(Color.GREEN)
            nextBtn.setTextColor(Color.WHITE)
        // Change to your desired color
        } else {
            nextBtn.setBackgroundColor(Color.LTGRAY)
            nextBtn.setTextColor(Color.WHITE)// Change to your desired color
        }
    }


    override fun backToSignIn() {
        Toast.makeText(MainActivity@this,"Back to Sign in ",Toast.LENGTH_LONG).show()
    }
}
