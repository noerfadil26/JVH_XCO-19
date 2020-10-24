package id.dwiilham.x_co19

import android.content.Context

class Preferences(c: Context)  {

    private val PREFS_NAME = "xcoPREF"

    val sharedPreferences = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setToken(v: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("token", v)
        editor.commit()
    }

    fun getToken() : String? {
        return sharedPreferences.getString("token", null)
    }
}