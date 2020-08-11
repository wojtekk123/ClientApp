package pl.e2d.clientapp.activities.timeScheduler

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import pl.e2d.clientapp.R
import pl.e2d.clientapp.adapter.PageAdapter

class FragmentMainPanel : AppCompatActivity()  {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example)


        val tabLayout = findViewById<TabLayout>(R.id.reservation_tab_layout)
        val viewPage = findViewById<ViewPager>(R.id.viewPageReservation)
        val pagerAdapter = PageAdapter( supportFragmentManager, tabLayout.tabCount)
        viewPage.adapter = pagerAdapter


        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.i("TextStats","NEW TAB SELECTED: " + tab.position)
                viewPage!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })

        viewPage!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

    }
}