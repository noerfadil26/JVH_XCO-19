package id.dwiilham.x_co19

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2

class DashboardActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var pagerAdapter: PagerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewPager = findViewById(R.id.view_pager)
        pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 1

        Log.d("checkPermissions", "START")
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "BEEN GRANTED")
            } else {
                ActivityCompat.requestPermissions(this, arrayOf<String>(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.INTERNET), 1)
            }
        } else {
            Log.d("SDKVERSION", Build.VERSION.SDK_INT.toString())
        }

    }
}
