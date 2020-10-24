package id.dwiilham.x_co19.callback

import com.google.gson.annotations.SerializedName

class SensorCallback {

    @SerializedName("coughState")
    var coughState: String? = null
    @SerializedName("coughOdd")
    var coughOdd: String? = null
    @SerializedName("tempState")
    var tempState: String? = null
    @SerializedName("tempOdd")
    var tempOdd: String? = null
    @SerializedName("oxyState")
    var oxyState: String? = null
    @SerializedName("oxyOdd")
    var oxyOdd: String? = null
    @SerializedName("location")
    var locState: String? = null
    @SerializedName("location_status")
    var locOdd: String? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("writeOn")
    var writeOn: String? = null

}
