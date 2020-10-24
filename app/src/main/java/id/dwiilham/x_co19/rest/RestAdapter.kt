package id.dwiilham.x_co19.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestAdapter {

    private var retrofit: Retrofit? = null

    val client: Retrofit?
    get() {
        if (retrofit == null) {
            val BASE_URL = "https://xco19.herokuapp.com/"
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }
}