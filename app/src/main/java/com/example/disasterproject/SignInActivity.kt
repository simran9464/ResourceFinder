package com.example.disasterproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.disasterproject.adapter.ProjectAdapter
import com.example.disasterproject.interfacemy.SignOutInterface
import com.example.disasterproject.object_item.Resource
import java.util.Locale

class SignInActivity : AppCompatActivity() {
    lateinit var welcomeMessage:TextView
    var fName:String=""
    var lName:String=""
    lateinit var btnsignOut:Button
    lateinit var signOutInterface:SignOutInterface
    private var categoryList=ArrayList<Resource>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProjectAdapter
//    shared prefernces is used to save login data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        var token =getSharedPreferences("username",Context.MODE_PRIVATE)
        fName= token.getString("loginusername"," ").toString()
        btnsignOut=findViewById(R.id.buttonSignOut)
        btnsignOut.setOnClickListener {
            var editor = token.edit()
            editor.putString("loginusername"," ")
            editor.commit()

            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        recyclerView = findViewById(R.id.catRes)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =ProjectAdapter(categoryList){resource ->
            val intent = Intent(this,ResourceLocationActivity::class.java)
            intent.putExtra("category",resource.categoryName)
            startActivity(intent)

        }
        recyclerView.adapter = adapter
        addDataToList()
        welcomeMessage=findViewById(R.id.textViewWelcome)
//        fName=intent.getStringExtra("fName").toString()
        lName=intent.getStringExtra("lName").toString()
        welcomeMessage.setText("Welcome 😊$fName in resFinder")


    }
//    private fun filterList(query : String?){
//        if(query!=null){
//            val filteredList = ArrayList<Resource>()
//            for(i in categoryList){
//                if(i.categoryName.lowercase(Locale.ROOT).contains(query) ||
//                    i.categoryName.uppercase(Locale.ROOT).contains(query.uppercase(Locale.ROOT))){
//                    filteredList.add(i)
//                }
//
//
//            }
//            if(filteredList.isEmpty()){
//                Toast.makeText(this,"No Resource Available",Toast.LENGTH_SHORT).show()
//            }
//            else{
//                adapter.setFilteredList(filteredList)
//            }
//        }
//    }
    private fun addDataToList() {
        categoryList.add(Resource(R.drawable.cloth,"Resource Information"
        )
        )

    }


}