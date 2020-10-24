package id.dwiilham.x_co19

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MAIN ACTIVITY", "AM HERE!")

    }

    override fun onStart() {
        super.onStart()

        val preferences = Preferences(this)

        val token = preferences.getToken()

        Log.d("TOKEN STATE", (token == "null").toString())
        Log.d("TOKEN", token.toString())

        if (token == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}
