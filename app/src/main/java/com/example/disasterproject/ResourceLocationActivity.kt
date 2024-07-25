package com.example.disasterproject

import android.content.ClipData.Item
import android.content.Intent
import android.health.connect.datatypes.ExerciseRoute.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.disasterproject.adapter.LocAdapter
import com.example.disasterproject.adapter.StepsAdapter
import com.example.disasterproject.database.DatabaseHelper
import com.example.disasterproject.database.StepsDatabase
import com.example.disasterproject.object_item.LocationModel
import com.example.disasterproject.object_item.StepsModel
import com.google.android.material.snackbar.Snackbar

class ResourceLocationActivity : AppCompatActivity() {
    lateinit var locationToWriteET : EditText
    lateinit var stepsEditText : EditText
    lateinit var locationActual:String
    lateinit var stepsActual:String
    lateinit var locationBtn: Button
    lateinit var btnToMap: Button
    lateinit var stepsBtn: Button
    lateinit var arrayListLocation :ArrayList<LocationModel>
    lateinit var arrayListSteps :ArrayList<StepsModel>
    lateinit var locRv :RecyclerView
    lateinit var stepsRv :RecyclerView
    lateinit var dbhelper :DatabaseHelper
    lateinit var stepsDb : StepsDatabase
    lateinit var locAdapter: LocAdapter
    lateinit var stepsAdapter :StepsAdapter
    lateinit var imgToMap:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_location2)
//        Initializing all the refernces
        initreferce()
        dbhelper = DatabaseHelper(this)
        locAdapter = LocAdapter(arrayListLocation){
                location ->
            dbhelper.deleteLocation(location.id)
            arrayListLocation.remove(location)
            locAdapter.notifyDataSetChanged()
        }
        locRv.layoutManager =LinearLayoutManager(this)
        locRv.adapter = locAdapter

        stepsDb = StepsDatabase(this)
        stepsAdapter = StepsAdapter(arrayListSteps){
            steps->
            stepsDb.deletesteps(steps.id)
            arrayListSteps.remove(steps)
            stepsAdapter.notifyDataSetChanged()
        }
        stepsRv.layoutManager=LinearLayoutManager(this)
        stepsRv.adapter = stepsAdapter

        val itemTouchHelper = ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean =false


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                locAdapter.removeItem(position)
            }

        })
        itemTouchHelper.attachToRecyclerView(locRv)

        val itemTouchHelperr = ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean =false


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                stepsAdapter.removeItem(position)
            }

        })
        itemTouchHelperr.attachToRecyclerView(stepsRv)



        arrayListLocation.addAll(dbhelper.getAllLocations())
        arrayListSteps.addAll(stepsDb.getAllLocations())

        stepsAdapter.notifyDataSetChanged()
        locationBtn.setOnClickListener {
           locationActual =locationToWriteET.text.toString()
            if(locationActual.isEmpty()){
                Toast.makeText(MainActivity@this,"No Location Provided",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val location =LocationModel(location=locationActual)
            dbhelper.addLocation(location)
            arrayListLocation.add(location)
            locAdapter.notifyDataSetChanged()



//            val user =LocationModel(locationActual)
//            arrayListLocation.add(user)
//            locRv.adapter = LocAdapter(arrayListLocation)
            Toast.makeText(MainActivity@this,"Location is added",Toast.LENGTH_LONG).show()
            locationToWriteET.text.clear()
        }
        imgToMap.setOnClickListener {
            val intent = Intent(this,MapActivity::class.java)
            startActivity(intent)
        }
        stepsBtn.setOnClickListener {
            stepsActual=stepsEditText.text.toString()
            if(stepsActual.isEmpty()){
                Toast.makeText(MainActivity@this,"No steps Provided",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val steps =StepsModel(steps=stepsActual)
            stepsDb.addLocation(steps)
            arrayListSteps.add(steps)
            stepsAdapter.notifyDataSetChanged()
            Toast.makeText(MainActivity@this,"Location is added",Toast.LENGTH_LONG).show()
            stepsEditText.text.clear()

        }
    }



    fun initreferce(){
        locationToWriteET =findViewById(R.id.locationToWriteET)
        stepsEditText =findViewById(R.id.stepsEditText)
        locationBtn =findViewById(R.id.locationBtn)
        stepsBtn=findViewById(R.id.stepsBtn)
        arrayListSteps= arrayListOf<StepsModel>()
        arrayListLocation= arrayListOf<LocationModel>()
        stepsRv =findViewById(R.id.stepsRV)
        locRv =findViewById(R.id.locRV)
        imgToMap=findViewById(R.id.imageGMap)


    }

}