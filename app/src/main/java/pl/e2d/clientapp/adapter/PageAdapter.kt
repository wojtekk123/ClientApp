package pl.e2d.clientapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import pl.e2d.clientapp.activities.timeScheduler.ReservationFragmentApprove
import pl.e2d.clientapp.activities.timeScheduler.ReservationFragmentDecline
import pl.e2d.clientapp.activities.timeScheduler.ReservationFragmentOpen
import java.lang.IllegalArgumentException

class PageAdapter (fm: FragmentManager,  var totalTabs: Int) : FragmentPagerAdapter (fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {return ReservationFragmentOpen()
            }
            1 -> {return ReservationFragmentApprove()
            }
            2 -> {return ReservationFragmentDecline()
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int {return totalTabs}


    override fun getItemId(position: Int): Long {
        return System.currentTimeMillis();
    }

}