package com.example.dogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.example.dogapp.Adapter.DogsAdapter
import com.example.dogapp.Model.DogsAPI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val imageList = ArrayList<DogsAPI>()
   // private lateinit var dogsRV:RecyclerView
   // private lateinit var dogNameText:EditText
   // private lateinit var searchBtn:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //dogsRV = findViewById(R.id.dogsrecyclerview)
       // dogNameText = findViewById(R.id.dogsearchET)
       // searchBtn = findViewById(R.id.searchbutton)

        dogsrecyclerview.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        searchbutton.setOnClickListener{
            var name = dogsearchET.text.toString()
            searchDogs(name)
        }
    }

    private fun searchDogs(name: String) {
        imageList.clear()
        AndroidNetworking.initialize(this)
        AndroidNetworking.get("https://dog.ceo/api/breed/$name/images")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object :StringRequestListener{
                override fun onResponse(response: String?) {
                    val result = JSONObject(response)
                    val image = result.getJSONArray("message")

                    for (i in 0 until image.length()){
                        val list = image.get(i)
                        imageList.add(
                            DogsAPI(list.toString())
                        )

                    }
                        dogsrecyclerview.adapter = DogsAdapter(this@MainActivity, imageList)

                }

                override fun onError(anError: ANError?) {

                }

            })

    }
}

