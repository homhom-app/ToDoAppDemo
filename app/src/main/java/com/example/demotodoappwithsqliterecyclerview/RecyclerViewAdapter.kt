package com.example.demotodoappwithsqliterecyclerview

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*


class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private val dbAdapter = DBAdapter(context)
    private var dataCursor = dbAdapter.getRecords()
    class MyViewHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false) as LinearLayout
        Log.d("View Holder","View Holder created")
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        dataCursor.moveToPosition(position)
        Log.d("View Holder","View Holder $position , cursor ${dataCursor.position} called")

        val taskName = dataCursor.getString(dataCursor.getColumnIndex("task"))
        val priorityName = dataCursor.getString(dataCursor.getColumnIndex("priority"))
        val remarksName = dataCursor.getString(dataCursor.getColumnIndex("remarks"))

        
        holder.layout.task.text = taskName
        holder.layout.priority.text = priorityName
        holder.layout.remarks.text = remarksName
        holder.layout.progress.isChecked = dataCursor.getInt(dataCursor.getColumnIndex("progress")) == 1
        holder.layout.progress.setOnClickListener {
            dataCursor.moveToPosition(position)
            Log.d("View Holder","checkbox position = $position, cursor = ${dataCursor.position} is touched")
            dbAdapter.updateProgress(dataCursor)
        }

        holder.layout.setOnLongClickListener {
            Log.d("View Holder","selected position: $position")
            AlertDialog.Builder(context)
                .setTitle("レコードの削除")
                .setMessage("$taskName , $priorityName , $remarksName を削除しますか?")
                .setPositiveButton(android.R.string.ok) {_,_ ->
                    dataCursor.moveToPosition(position)
                    dbAdapter.deleteRecord(dataCursor)
                    Log.d("View Holder","******* deleted cursor: $dataCursor *******")
                    Log.d("View Holder","item count = $itemCount, db size = ${dataCursor.count} after deleted")
                    //notifyDataSetChanged()
                    dataCursor = dbAdapter.getRecords()
                    notifyDataSetChanged()
                }
                .setNegativeButton(android.R.string.no){_,_->}.create().show()
            true
        }
    }

    override fun getItemCount() : Int {
        Log.d("View Holder","getremarksCount() = ${dataCursor.count}")
        return dataCursor.count
    }
}
