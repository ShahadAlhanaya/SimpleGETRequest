package com.example.simplegetrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_text_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class TextViewActivity : AppCompatActivity() {

    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)
        //actionbar
        val actionbar = supportActionBar!!
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        textView = findViewById(R.id.textView)
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
                            withContext(Main){
                                textView.text = "${textView.text}\n$name"
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("TextViewActivity", e.message.toString())
            }
        }
    }
}