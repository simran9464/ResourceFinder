package com.example.disasterproject
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
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
    lateinit var btnCall:Button
//    shared prefernces is used to save login data
companion object {
    const val REQUEST_CALL_PHONE = 1
    const val PHONE_NUMBER = "tel:211" // Replace with the actual phone number
}
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
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeItem(position)
            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
        addDataToList()
        welcomeMessage=findViewById(R.id.textViewWelcome)
//        fName=intent.getStringExtra("fName").toString()
        lName=intent.getStringExtra("lName").toString()
        welcomeMessage.setText("Welcome 😊$fName in resFinder")
        btnCall = findViewById(R.id.buttonCall)
        btnCall.setOnClickListener {
            makePhoneCall()
        }

    }
    private fun makePhoneCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PHONE)
        } else {
            val callIntent = Intent(Intent.ACTION_CALL, Uri.parse(PHONE_NUMBER))
            startActivity(callIntent)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
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