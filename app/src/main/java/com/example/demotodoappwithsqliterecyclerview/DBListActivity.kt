package com.example.demotodoappwithsqliterecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DBListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dblist)
        title = "ToDo List"

        adaptView()
        Log.d("DB List Activity","onCreate called: context = $this")

    }


    private fun adaptView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("DB List Activity","onDestroy called: context = $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d("DB List Activity","onPause called: context = $this")
        adaptView()
    }
}
