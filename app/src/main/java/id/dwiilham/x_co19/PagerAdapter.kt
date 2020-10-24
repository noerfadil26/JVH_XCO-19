package id.dwiilham.x_co19

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        when(position) {
            0 -> {
                return ProfilFragment().apply {  }
            }
            1 -> {
                return ConditionFragment().apply {  }
            }
            else -> {
                return BeritaFragment().apply {  }
            }
        }
    }



}