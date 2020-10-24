package id.dwiilham.x_co19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import id.dwiilham.x_co19.callback.LoginCallback
import id.dwiilham.x_co19.model.Login
import id.dwiilham.x_co19.rest.RestAdapter
import id.dwiilham.x_co19.rest.RestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var textEmail: EditText
    private lateinit var textPass: EditText
    private lateinit var textWarn: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnRegist: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textEmail = findViewById(R.id.login_email)
        textPass = findViewById(R.id.login_pass)
        textWarn = findViewById(R.id.login_warn)
        btnLogin = findViewById(R.id.btn_login)
        btnRegist = findViewById(R.id.btn_register_onLogin)

        Log.d("LOGIN ACTIVITY", "AM HERE")

    }

    override fun onStart() {
        super.onStart()

        if (Preferences(this).getToken() != null) {
            Log.d("LoginActivity", "Going to DashboardActivity")
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            btnLogin.setOnClickListener {
                if (textEmail.text.length > 1 && textPass.text.length > 1) {
                    doLogin(textEmail.text.toString(), textPass.text.toString())
                } else {
                    textWarn.text = "Fill in the fields carefully"
                }
            }

            btnRegist.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun doLogin(s: String, s1: String) {

        Log.d("doLogin", "START")

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<LoginCallback>? = api?.postLogin(Login(s, s1))
        call?.enqueue(object : Callback<LoginCallback> {
            override fun onFailure(call: Call<LoginCallback>?, t: Throwable?) {
                Toast.makeText(this@LoginActivity, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("Login", t.toString())
            }

            override fun onResponse(call: Call<LoginCallback>?, response: Response<LoginCallback>?) {

                if (response?.isSuccessful!!) {
                    Log.d("login process", response.code().toString())
                    when(response.code()) {
                        200 -> {
                            Log.d("Login 2", response.body().msg.toString())
                            Preferences(this@LoginActivity).setToken(response.body().msg.toString())
                            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_LONG)
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        500 -> {
                            textWarn.text = "system error"
                        }
                        else -> {
                            textWarn.text = "Fill in the fields carefully"
                        }
                    }
                } else {
                    textWarn.text = "Fill in the fields carefully"
                }
            }

        })
    }
}
