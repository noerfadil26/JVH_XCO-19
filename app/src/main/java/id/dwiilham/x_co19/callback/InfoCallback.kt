package id.dwiilham.x_co19.callback

import com.google.gson.annotations.SerializedName
import java.sql.Struct

class InfoCallback {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("nohp")
    var nohp: String? = null
}
