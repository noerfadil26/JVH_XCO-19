package id.dwiilham.x_co19.callback

import com.google.gson.annotations.SerializedName

class CovidCallback {

    @SerializedName("provinsi")
    var provinsi: String? = null

    @SerializedName("positif")
    var positif: Int? = null

    @SerializedName("sembuh")
    var sembuh: Int? = null

    @SerializedName("meninggal")
    var meninggal: Int? = null

}
