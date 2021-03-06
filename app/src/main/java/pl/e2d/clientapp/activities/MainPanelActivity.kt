package pl.e2d.clientapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.basic_activity.*
import pl.e2d.clientapp.R
import pl.e2d.clientapp.activities.masterData.StudentPanel
import pl.e2d.clientapp.activities.timeScheduler.FragmentMainPanel

class MainPanelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_activity)

        manageStudent.setOnClickListener {
            startActivity(Intent(this@MainPanelActivity,StudentPanel::class.java))
        }

        manageReservation.setOnClickListener {
            startActivity(Intent(this@MainPanelActivity, FragmentMainPanel::class.java))
        }
    }
}