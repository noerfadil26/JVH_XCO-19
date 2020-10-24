package id.dwiilham.x_co19

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import id.dwiilham.x_co19.callback.InfoCallback
import id.dwiilham.x_co19.callback.LogoutCallback
import id.dwiilham.x_co19.callback.NohpCallback
import id.dwiilham.x_co19.rest.RestAdapter
import id.dwiilham.x_co19.rest.RestInterface
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilFragment: Fragment() {

    private lateinit var txtProfilName: TextView
    private lateinit var txtProfilEmail: TextView
    private lateinit var btnProfilLogout: Button
    private lateinit var btnProfilBack: Button
    private lateinit var txtNohpUpdate: TextView
    private lateinit var btnNohpUpdate: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.profil_fragment, container, false)
        Log.d("PROFIL FRAGMENT", "AM WAKE UP")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtProfilName = view.findViewById(R.id.txt_profil_name)
        txtProfilEmail = view.findViewById(R.id.txt_profil_email)
        txtNohpUpdate = view.findViewById(R.id.txt_nohp_update)
        btnNohpUpdate = view.findViewById(R.id.btn_nohp_update)
        btnProfilLogout = view.findViewById(R.id.btn_profil_logout)
        btnProfilBack = view.findViewById(R.id.btn_profil_back)

        getProfilInfo(view)

        btnProfilLogout.setOnClickListener {
            doLogout(it)
        }

        btnProfilBack.setOnClickListener {
            //val pager = DashboardActivity().viewPager
            //pager.currentItem = 1
        }

        btnNohpUpdate.setOnClickListener {
            doNohpUpdate()
        }

    }

    private fun doNohpUpdate() {

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<NohpCallback>? = api?.postNohp("Bearer " + this!!.context?.let { Preferences(it).getToken() },
            txtNohpUpdate.text as String?
        )

        call?.enqueue(object : Callback<NohpCallback> {
            override fun onFailure(call: Call<NohpCallback>?, t: Throwable?) {
                Toast.makeText(context, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("postNohp", t.toString())
            }

            override fun onResponse(call: Call<NohpCallback>?, response: Response<NohpCallback>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Update No. HP berhasil!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "there is an error", Toast.LENGTH_LONG).show()
                        Log.e("doLogout", "isn't successful")
                    }
                } else {
                    Toast.makeText(context, "there is an error", Toast.LENGTH_LONG).show()
                    Log.e("postNohp", "is null")
                }
            }

        })
    }

    private fun doBack(v: View?) {
        Log.d("doBack", "CLICKED!")
    }

    private fun doLogout(v: View) {

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<LogoutCallback>? = api?.doLogout("Bearer " + Preferences(v.context).getToken())

        call?.enqueue(object : Callback<LogoutCallback> {
            override fun onFailure(call: Call<LogoutCallback>?, t: Throwable?) {
                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("doLogout", t.toString())
            }

            override fun onResponse(call: Call<LogoutCallback>?, response: Response<LogoutCallback>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        when(response.code()) {
                            200 -> {
                                Preferences(v.context).setToken(null)
                                val intent = Intent(v.context, LoginActivity::class.java)

                                startActivity(intent)
                                activity!!.finish()
                            }
                        }
                    }
                }
            }

        })
    }

    private fun getProfilInfo(v: View) {

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<InfoCallback>? = api?.getInfo("Bearer " + Preferences(v.context).getToken())

        call?.enqueue(object : Callback<InfoCallback> {
            override fun onFailure(call: Call<InfoCallback>?, t: Throwable?) {
                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("getInfo", t.toString())
            }

            override fun onResponse(call: Call<InfoCallback>?, response: Response<InfoCallback>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        when(response.code()) {
                            200 -> {
                                txtProfilEmail.text = response.body().email
                                txtProfilName.text = response.body().name
                                txtNohpUpdate.text = response.body().nohp
                            }
                            else -> {
                                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                                Log.e("getInfo", "response code isn't 200OK")
                            }
                        }
                    } else {
                        Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                        Log.e("getInfo", "response code isn't 200OK")
                    }
                } else {
                    Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                    Log.e("getInfo", "the response can't loaded carefully")
                }
            }

        })
    }

}