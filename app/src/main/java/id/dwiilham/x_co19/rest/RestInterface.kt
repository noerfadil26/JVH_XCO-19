package id.dwiilham.x_co19.rest

import id.dwiilham.x_co19.callback.*
import id.dwiilham.x_co19.model.Loc
import id.dwiilham.x_co19.model.Login
import id.dwiilham.x_co19.model.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RestInterface {

    @POST("auth/login")
    fun postLogin(
        @Body body: Login?
    ): Call<LoginCallback>?

    @POST("auth/register")
    fun postRegister(
        @Body body: Register?
    ): Call<RegisterCallback>?

    @GET("user/info")
    fun getInfo(
        @Header("authorization") authorization: String?
    ): Call<InfoCallback>?

    @GET("user/sensor")
    fun getSensor(
        @Header("authorization") authorization: String?
    ): Call<SensorCallback>?

    @POST("user/gps")
    fun setLoc(
        @Header("authorization") authorization: String?,
        @Body body: Loc?
    ): Call<LocCallback>?

    @POST("/covid")
    fun getCovid(
        @Body body: Loc?
    ): Call<CovidCallback>

    @GET("/berita")
    fun getBerita(): Call<List<BeritaCallback>>

    @GET("user/logout")
    fun doLogout(
        @Header("authorization") authorization: String?
    ): Call<LogoutCallback>?

    @POST("user/nohp")
    fun postNohp(
        @Header("authorization") authorization: String?,
        @Body nohp: String?
    ): Call<NohpCallback>?

}