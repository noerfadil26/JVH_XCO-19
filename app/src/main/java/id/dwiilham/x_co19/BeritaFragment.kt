package id.dwiilham.x_co19

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.LocationServices
import id.dwiilham.x_co19.callback.BeritaCallback
import id.dwiilham.x_co19.callback.CovidCallback
import id.dwiilham.x_co19.model.Loc
import id.dwiilham.x_co19.rest.RestAdapter
import id.dwiilham.x_co19.rest.RestInterface
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.berita_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeritaFragment: Fragment() {

    private lateinit var txtBeritaPositif: TextView
    private lateinit var txtBeritaSembuh: TextView
    private lateinit var txtBeritaMeninggal: TextView
    private lateinit var listBerita: RecyclerView
    private lateinit var txtBeritaProvince: TextView
    private lateinit var btnBeritaBack: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.berita_fragment, container, false)
        Log.d("BERITA FRAGMENT", "AM WAKE UP")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtBeritaPositif = view.findViewById(R.id.txt_berita_positif)
        txtBeritaSembuh = view.findViewById(R.id.txt_berita_sembuh)
        txtBeritaMeninggal = view.findViewById(R.id.txt_berita_meninggal)
        listBerita = view.findViewById(R.id.list_berita_berita)
        txtBeritaProvince = view.findViewById(R.id.txt_berita_province)
        btnBeritaBack = view.findViewById(R.id.btn_berita_back)

        btnBeritaBack.setOnClickListener {
            //val pager = DashboardActivity().viewPager
            //pager.currentItem = 1
        }

        loadBerita(view)

        val location = LocationServices.getFusedLocationProviderClient(view.context)
        location.lastLocation.addOnSuccessListener {
            Log.d("location", it.longitude.toString() + " Latitude: " + it.latitude.toString())

            getCovidInfo(view, it.latitude.toString(), it.longitude.toString())

        }

        location.lastLocation.addOnFailureListener {
            Log.d("error loc", it.toString())
        }
    }

    private fun getCovidInfo(v: View, s: String, s1: String) {
        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<CovidCallback>? = api?.getCovid(Loc(s, s1))
        call?.enqueue(object : Callback<CovidCallback> {
            override fun onFailure(call: Call<CovidCallback>?, t: Throwable?) {
                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("getCovid", t.toString())
            }

            override fun onResponse(call: Call<CovidCallback>?, response: Response<CovidCallback>?) {
                Log.d("getCovid Status", response?.code().toString())

                if (response != null) {
                    if (response.isSuccessful) {
                        when(response.code()) {
                            200 -> {
                                txtBeritaProvince.text = response.body().provinsi.toString()
                                txtBeritaPositif.text = response.body().positif.toString()
                                txtBeritaSembuh.text = response.body().sembuh.toString()
                                txtBeritaMeninggal.text = response.body().meninggal.toString()
                            }
                            else -> {
                                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                                Log.e("getCovid", "isn't 200OK")
                            }
                        }
                    } else {
                        Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                        Log.e("getCovid", "isn't successfull")
                    }
                } else {
                    Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                    Log.e("getCovid", "is null")
                }
            }

        })
    }

    private fun loadBerita(v: View) {

        val api = RestAdapter.client?.create(RestInterface::class.java)

        val call: Call<List<BeritaCallback>>? = api?.getBerita()
        call?.enqueue(object : Callback<List<BeritaCallback>> {
            override fun onFailure(call: Call<List<BeritaCallback>>?, t: Throwable?) {
                Toast.makeText(v.context, "there is an error", Toast.LENGTH_LONG).show()
                Log.e("loadBerita", t.toString())
            }

            override fun onResponse(call: Call<List<BeritaCallback>>?, response: Response<List<BeritaCallback>>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        when(response.code()) {
                            200 -> {
                                val listBerita = view!!.findViewById<RecyclerView>(R.id.list_berita_berita).apply {
                                    setHasFixedSize(true)
                                    layoutManager = LinearLayoutManager(v.context)
                                    adapter = BeritaAdapter(v.context, response.body())
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}