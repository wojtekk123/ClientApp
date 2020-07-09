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
        val id = view.findViewById<TextView>(R.id.textView_id)
        val name = view.findViewById<TextView>(R.id.textView_name)
        val secondName = view.findViewById<TextView>(R.id.textView_secondName)

        id.text = list[position].id.toString()
        name.text = list[position].user.firstName
        secondName.text = list [position].user.secondName
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

//class ListAdapterPopUp  {
//     fun getView( student:Student,  context: Context) {
//
//        val view:View = LayoutInflater.from(context)
//        val firstName = view.findViewById<TextView>(R.id.pp_firstName)
//        val secondName = view.findViewById<TextView>(R.id.pp_secondName)
//        val email = view.findViewById<TextView>(R.id.pp_email)
//        val phone = view.findViewById<TextView>(R.id.pp_phone)
//        val schoolId = view.findViewById<TextView>(R.id.pp_schoolId)
//        val startDate = view.findViewById<TextView>(R.id.pp_startDate)
//        val endDate = view.findViewById<TextView>(R.id.pp_endDate)
//
//        firstName.text = list [position].user.firstName
//        secondName.text = list [position].user.secondName
//        email.text = list [position].user.email
//        phone.text = list [position].user.phoneNumber
//        schoolId.text = list [position].schoolId.toString()
//        startDate.text = list [position].startEducation
//        endDate.text = list [position].endEducation
//
//    }
}