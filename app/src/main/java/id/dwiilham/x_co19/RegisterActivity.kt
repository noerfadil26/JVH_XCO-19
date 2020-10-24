package id.dwiilham.x_co19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import id.dwiilham.x_co19.callback.RegisterCallback
import id.dwiilham.x_co19.model.Register
import id.dwiilham.x_co19.rest.RestAdapter
import id.dwiilham.x_co19.rest.RestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var pass2:EditText
    private lateinit var tnc: CheckBox
    private lateinit var warn: TextView
    private lateinit var btnRegist: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById(R.id.regist_name)
        email = findViewById(R.id.regist_email)
        pass = findViewById(R.id.regist_pass)
        pass2 = findViewById(R.id.regist_pass2)
        tnc = findViewById(R.id.regist_tnc)
        tnc.isChecked = true

        warn = findViewById(R.id.register_warn)
        btnRegist = findViewById(R.id.btn_regist)

    }

    override fun onStart() {
        super.onStart()

        btnRegist.setOnClickListener {

            Log.d("Register 1", (pass2.text == pass.text).toString())

            if (pass.text.toString() == pass2.text.toString() && email.text.length > 1 && name.text.length > 1 && pass.text.length > 1 && tnc.isChecked) {
                Log.d("Register 2", "do")
                doRegister(name, email, pass, tnc)
            } else {
                Log.d("Register 3", "error  ")
                warn.text = "Fill in the fields carefully"
            }

        }

    }

    private fun doRegister(name: EditText, email: EditText, pass: EditText, tnc: CheckBox) {

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<RegisterCallback>? = api?.postRegister(Register(
            name.text.toString(),
            email.text.toString(),
            pass.text.toString(),
            tnc.isChecked
        ))
        call?.enqueue(object : Callback<RegisterCallback> {
            override fun onFailure(call: Call<RegisterCallback>?, t: Throwable?) {
                Toast.makeText(this@RegisterActivity, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("register", t.toString())
            }

            override fun onResponse(call: Call<RegisterCallback>?, response: Response<RegisterCallback>?) {
                Log.d("Register", response?.isSuccessful.toString())

                if (response?.isSuccessful!!) {
                    Log.d("login process", response.code().toString())

                    when(response.code()) {
                        200 -> {
                            Toast.makeText(this@RegisterActivity, "Login Success", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java) // Pindah slide (Intent)
                            startActivity(intent)
                            finish()
                        }
                        else -> {
                            Toast.makeText(this@RegisterActivity, "there is an error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }


        })
    }
}
