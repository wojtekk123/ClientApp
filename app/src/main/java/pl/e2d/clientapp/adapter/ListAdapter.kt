package pl.e2d.clientapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pl.e2d.clientapp.R
import pl.e2d.clientapp.model.masterDataEntity.Student

class ListAdapter(val context: Context, val list: List<Student>) : BaseAdapter() {
    override fun getView(position: Int, currentView: View?, parent: ViewGroup?): View {

        val view: View =
            LayoutInflater.from(context).inflate(R.layout.adapter_vie_layout, parent, false)
        val id = view.findViewById<TextView>(R.id.textView_id)
        val name = view.findViewById<TextView>(R.id.textView_name)
        val secondName = view.findViewById<TextView>(R.id.textView_secondName)

        id.text = position.toString()
        name.text = list[position].user.firstName
        secondName.text = list[position].user.secondName
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

class AdapterPopUpList(val context: Context, val list: List<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.adapter_vie_popup_list, parent, false)
        val text = view.findViewById<TextView>(R.id.textView_popUp)
        text.text = list[position]
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

}class AdapterPopUpAdd(val context: Context, val list: List<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.adapter_vie_popup_list, parent, false)
        val text = view.findViewById<TextView>(R.id.textView_popUp)
        text.hint = list[position]
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