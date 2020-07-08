package pl.e2d.clientapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pl.e2d.clientapp.R
import pl.e2d.clientapp.model.masterDataEntity.Student

 class ListAdapter (val context: Context, val list:ArrayList<Student>):BaseAdapter() {
    override fun getView(position: Int, currentView: View?, parent: ViewGroup?): View {

        val view:View = LayoutInflater.from(context).inflate(R.layout.adapter_vie_layout, parent,false)

        val id = view.findViewById<TextView>(R.id.id_textView)
        val user = view.findViewById<TextView>(R.id.user_textView)
        val schoolId = view.findViewById<TextView>(R.id.schoolId_textView)
        val startDate = view.findViewById<TextView>(R.id.startDate_textView)
        val endDate = view.findViewById<TextView>(R.id.stopDate_textView)

        id.text = list[position].id.toString()
        user.text = list[position].user.firstName
        schoolId.text = list [position].longId.toString()
        startDate.text = list [position].startEducation.toString()
        endDate.text = list [position].startEducation.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}