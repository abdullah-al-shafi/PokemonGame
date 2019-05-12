package shafi.sbf.com.pokemongame

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermition()
        LoadPokemon()
    }

var ACCESSLOCATION=123
    fun checkPermition(){

        if (Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESSLOCATION)
                return
            }
        }
        GetUserLocation()
    }
    fun GetUserLocation(){
        Toast.makeText(this,"User location access on",Toast.LENGTH_LONG).show()
        //TODO: Will Implement later
        var myLocation = MylocationListener()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)

        var mythread = myThread()
        mythread.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            ACCESSLOCATION->{
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    GetUserLocation()
                }
                else{
                    Toast.makeText(this,"We Can't access your permission",Toast.LENGTH_LONG).show()
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera


    }

    //Get user location
    var loca:Location?=null
    inner class MylocationListener:LocationListener{

        constructor(){
            loca= Location("Start")
            loca!!.longitude=0.0
            loca!!.longitude=0.0
        }
        override fun onLocationChanged(location: Location?) {
            loca=location
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {
        }

    }

    var oldLocation:Location?=null
    inner class myThread:Thread{
        constructor():super(){
            oldLocation= Location("Start")
            oldLocation!!.longitude=0.0
            oldLocation!!.longitude=0.0
        }

        override fun run() {
            while (true){
                try {
                    if (oldLocation!!.distanceTo(loca)==0f){
                        continue
                    }
                    oldLocation=loca
                    runOnUiThread {
                        mMap.clear()

                        //show me
                    val dhaka = LatLng(loca!!.latitude, loca!!.longitude)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(dhaka)
                            .title("Me")
                            .snippet("Here is my location")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario))
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 18f))

                        //Show pokemon

                        for (i in 0..listPokemon.size-1){
                            var newPokemon=listPokemon[i]
                            if (newPokemon.IsCatch==false){
                                val pokemonLoc = LatLng(newPokemon.locationPo!!.latitude,newPokemon.locationPo!!.longitude)
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(pokemonLoc)
                                        .title(newPokemon.name!!)
                                        .snippet(newPokemon.ads!!+" Power:"+newPokemon!!.power)
                                        .icon(BitmapDescriptorFactory.fromResource(newPokemon.image!!)))

                                if (loca!!.distanceTo(newPokemon.locationPo)<2){
                                    newPokemon.IsCatch=true
                                    listPokemon[i]=newPokemon
                                    playerPower=newPokemon.power!!
                                    Toast.makeText(this@MapsActivity,"You catch a new pokemon your new power is "+playerPower,
                                        Toast.LENGTH_LONG).show()
                                }

                            }
                        }
                }
                    Thread.sleep(1000)
                }catch (ex:Exception){}
            }
        }
    }
var playerPower=0.0;
    var listPokemon=ArrayList<Pokemon>()
    fun LoadPokemon(){
        listPokemon.add(Pokemon(R.drawable.charmander,"Charmander","Charmander live in Dhaka",55.0,23.7730072,90.3532024)
        )
        listPokemon.add(Pokemon(R.drawable.bulbasaur,"bulbasaur","Charmander live in Naogoan",90.5,23.7710805,90.3544127)
        )
        listPokemon.add(Pokemon(R.drawable.squirtle,"squirtle","Charmander live in Rajshahi",33.5,23.77241,90.3553929)
        )
        listPokemon.add(Pokemon(R.drawable.aaaa,"aaaa","Charmander live in Khulna",33.5,23.7506915,90.3771456)
        )
        listPokemon.add(Pokemon(R.drawable.bbb,"bbbb","Charmander live in Kustia",69.8,23.7506915,90.3771456)
        )
        listPokemon.add(Pokemon(R.drawable.ccc,"cccc","Charmander live in Meherpur",56.5,23.7506203,90.3788514)
        )

    }

}
