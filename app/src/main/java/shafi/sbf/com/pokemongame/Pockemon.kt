package shafi.sbf.com.pokemongame

import android.location.Location

class Pokemon{
    var name:String?=null
    var ads:String?=null
    var image:Int?=null
    var power:Double?=null

    var IsCatch:Boolean?=false
    var locationPo:Location?=null
    constructor(image:Int,name:String,ads:String,power:Double,lat:Double,log:Double){
        this.name=name
        this.image=image
        this.ads=ads
        this.power=power
        this.locationPo= Location(name)
        this.locationPo!!.latitude=lat
        this.locationPo!!.longitude=log
        this.IsCatch=false
    }
}