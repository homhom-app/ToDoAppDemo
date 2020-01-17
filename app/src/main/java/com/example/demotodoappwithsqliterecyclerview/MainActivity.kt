package com.example.demotodoappwithsqliterecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
lateinit var prioritySelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "ToDo App."

        createSpinner()
        db_button.setOnClickListener {
            val intent = Intent(this, DBListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            priority_spinner.adapter = adapter
        }
        priority_spinner.onItemSelectedListener = spinnerListener
    }

    private val spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            prioritySelected = parent?.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            prioritySelected = ""
        }
    }

    fun registerButtonClicked(view: View) {
        val dbAdapter = DBAdapter(applicationContext)
        if (task_name.text.toString().isEmpty() and remarks_text.text.toString().isEmpty())
            Toast.makeText(this, "内容がありません", Toast.LENGTH_LONG).show()
        else {
            dbAdapter.addRecord(task_name.text.toString(), prioritySelected.split(":")[0].toInt(), remarks_text.text.toString())
            Log.d("DB Register","stage = ${task_name.text}, condition = $prioritySelected, item_text = ${remarks_text.text}")
            Toast.makeText(this,"登録しました", Toast.LENGTH_LONG).show()
            createSpinner()
            task_name.apply { text = null }
            remarks_text.apply { text = null }
        }
    }

}
