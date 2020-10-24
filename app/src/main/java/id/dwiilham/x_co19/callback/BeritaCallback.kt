package id.dwiilham.x_co19.callback

import com.google.gson.annotations.SerializedName

class BeritaCallback {

    @SerializedName("title")
    var title: String? = null

    @SerializedName("time")
    var time: String? = null

    @SerializedName("artikel")
    var artikel: String? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("url")
    var url: String? = null

}
