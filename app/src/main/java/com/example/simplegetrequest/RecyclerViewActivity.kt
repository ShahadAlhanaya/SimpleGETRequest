package com.example.simplegetrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class RecyclerViewActivity : AppCompatActivity() {

    var namesList = arrayListOf<String>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_activity)
        //actionbar
        val actionbar = supportActionBar!!
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        //initialize recyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RVAdapter(namesList)

        requestData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun requestData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val okHttpClient = OkHttpClient()
                val request = Request.Builder()
                    .url("https://dojo-recipes.herokuapp.com/people/")
                    .build()
                val response =
                    withContext(Dispatchers.Default) {
                        okHttpClient.newCall(request).execute()
                    }
                if (response != null) {
                    if (response.code == 200) {
                        val jsonArray = JSONArray(response.body!!.string())
                        Log.d("HELP", jsonArray.toString())
                        for(index in 0 until jsonArray.length()){
                            val nameObj = jsonArray.getJSONObject(index)
                            val name = nameObj.getString("name")
                            namesList.add(name)
                        }
                        withContext(Main){
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("TextViewActivity", e.message.toString())
            }
        }
    }
}