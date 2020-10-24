package id.dwiilham.x_co19

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.LocationServices
import id.dwiilham.x_co19.callback.LocCallback
import id.dwiilham.x_co19.callback.SensorCallback
import id.dwiilham.x_co19.model.Loc
import id.dwiilham.x_co19.rest.RestAdapter
import id.dwiilham.x_co19.rest.RestInterface
import kotlinx.android.synthetic.main.activity_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConditionFragment: Fragment() {

    private lateinit var txtConditionOxy: TextView
    private lateinit var txtConditionOxyOdd: TextView
    private lateinit var txtConditionTemp: TextView
    private lateinit var txtConditionTempOdd: TextView
    private lateinit var txtConditionHt: TextView
    private lateinit var txtConditionhtOdd: TextView
    private lateinit var txtConditionLoc: TextView
    private lateinit var txtConditionLocOdd: TextView
    private lateinit var txtConditionStatus: TextView
    private lateinit var btnConditionToProfil: Button
    private lateinit var btnConditionToBerita: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.condition_fragment, container, false)
        Log.d("CONDITION FRAGMENT", "AM WAKE UP")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtConditionOxy = view.findViewById(R.id.txt_condition_oxy)
        txtConditionOxyOdd = view.findViewById(R.id.txt_condition_oxy_odd)
        txtConditionTemp = view.findViewById(R.id.txt_condition_temp)
        txtConditionTempOdd = view.findViewById(R.id.txt_condition_temp_odd)
        txtConditionHt = view.findViewById(R.id.txt_condition_ht)
        txtConditionhtOdd = view.findViewById(R.id.txt_condition_ht_odd)
        txtConditionLoc = view.findViewById(R.id.txt_condition_loc)
        txtConditionLocOdd = view.findViewById(R.id.txt_condition_loc_odd)
        txtConditionStatus = view.findViewById(R.id.txt_condition_status)
        btnConditionToProfil = view.findViewById(R.id.btn_condition_to_profil)
        btnConditionToBerita = view.findViewById(R.id.btn_condition_to_berita)

        getSensor(view)

        var runnable = Runnable {  }

        var handler = Handler()

        val delay = 3000

        handler.postDelayed(Runnable {
            activity?.runOnUiThread {
                handler.postDelayed(runnable, delay.toLong())
                getSensor(view)
                val location = LocationServices.getFusedLocationProviderClient(view.context)
                location.lastLocation.addOnSuccessListener {
                    Log.d("location", it.toString())

                    upLoc(view, it.latitude.toString(), it.longitude.toString())

                }

                location.lastLocation.addOnFailureListener {
                    Log.d("error loc", it.toString())
                }
            }
        }.also { runnable = it }, delay.toLong())


        //val view_pager = DashboardActivity().viewPager

        btnConditionToBerita.setOnClickListener {
            //view_pager.currentItem = 2
        }
        btnConditionToProfil.setOnClickListener {
            //view_pager.currentItem = 0
        }

    }

    private fun getSensor(v: View) {

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<SensorCallback>? = api?.getSensor("Bearer " + Preferences(v.context).getToken())

        call?.enqueue(object : Callback<SensorCallback> {
            override fun onFailure(call: Call<SensorCallback>?, t: Throwable?) {
                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("getSensor", t.toString())
            }

            override fun onResponse(call: Call<SensorCallback>?, response: Response<SensorCallback>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        when(response.code()) {
                            200 -> {
                                txtConditionOxy.text = response.body().coughState
                                txtConditionOxyOdd.text = response.body().coughOdd
                                txtConditionTemp.text = response.body().tempState
                                txtConditionTempOdd.text = response.body().tempOdd
                                txtConditionHt.text = response.body().oxyState
                                txtConditionhtOdd.text = response.body().oxyOdd
                                //txtConditionLoc.text = response.body().locState
                                //txtConditionLocOdd.text = response.body().locOdd
                                txtConditionStatus.text = response.body().status
                            }
                            else -> {
                                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                                Log.e("getSensor", "http code doesn't 200OK")
                            }
                        }
                    } else {
                        Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                        Log.e("getSensor", "request isn't successfull")
                    }
                } else {
                    Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                    Log.e("getSensor", "response is null")
                }
            }

        })

    }

    private fun upLoc(v: View, s: String, s1: String) {

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<LocCallback>? = api?.setLoc("Bearer " + Preferences(v.context).getToken(), Loc(s, s1))
        call?.enqueue(object: Callback<LocCallback> {
            override fun onFailure(call: Call<LocCallback>?, t: Throwable?) {
                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("getLoc", t.toString())
            }

            override fun onResponse(call: Call<LocCallback>?, response: Response<LocCallback>?) {
                Log.d("setLoc", response?.code().toString())

                if (response?.isSuccessful!!) {
                    when(response.code()) {
                        200 -> {
                            txtConditionLoc.text = response.body().state.toString()
                            txtConditionLocOdd.text = response.body().city.toString()
                        }
                        400 -> {
                            Log.d("setLoc", "Been here before")
                        }
                        else -> {
                            Toast.makeText(v.context, "cannot access the location", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(v.context, "cannot access the location", Toast.LENGTH_LONG).show()
                }
            }

        })

    }

}