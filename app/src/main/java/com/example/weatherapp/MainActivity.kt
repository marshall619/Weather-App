package com.example.weatherapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData_tehran()

    }



    fun sets(
        name: String,
        weatherDis: String,
        temp: Double,
        ImagUrl: String,
        sunRize: Int,
        sunSet: Int,
        feelsLike: Double,
        temp_max: Double,
        temp_min: Double,
        pressure: Int,
        humidity: Double,
        speed: Double,
        deg: Int
    ){
            binding.imageView.visibility =  View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
            binding.textViewcityname.text = name
            binding.textViewWeather.text = weatherDis
            binding.textViewtemp.text = "دما : $temp"
            binding.textViewsunRise.text = Timeunx(sunRize)
            binding.textViewSunSet.text = Timeunx(sunSet)
            binding.textViewtempMin.text = "بیشترین : $temp_min"
            binding.textViewtempMax.text = "کمترین : $temp_max"
            binding.textViewfeelsLike.text = "احساس : $feelsLike"
            binding.textViewpressure.text = "فشار : $pressure"
            binding.textViewhumidity.text = "رطوبت : $humidity"
            binding.textViewDegri.text = "درجه : $deg"
            binding.textViewWind.text = "سرعت باد : $speed"
            Glide.with(this@MainActivity).load(ImagUrl).into(binding.imageViewphoto)
    }




    fun Timeunx(unix:Int):String{
       val TimeMili = unix * 1000 .toLong()
        val date = Date(TimeMili)
        val formatter = SimpleDateFormat("HH:mm a")
        return formatter.format(date)

    }



    private fun getData_bush(){
        var client = OkHttpClient()
        var request = Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?q=bushehr&appid=8e2c4853730bbc1a089bdbe682089265&lang=fa&units=metric").build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("tagx","download falid!!")
            }

            override fun onResponse(call: Call, response: Response) {
                val rowresponse = response.body!!.string()
                getFromServer(rowresponse)

            }

        })
    }


    private fun getData_mash(){
        var client = OkHttpClient()
        var request = Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?q=mashhad&appid=8e2c4853730bbc1a089bdbe682089265&lang=fa&units=metric").build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("tagx","download falid!!")
            }

            override fun onResponse(call: Call, response: Response) {
                val rowresponse = response.body!!.string()
                getFromServer(rowresponse)

            }

        })
    }


    private fun getData_tab(){
        var client = OkHttpClient()
        var request = Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?q=tabriz&appid=8e2c4853730bbc1a089bdbe682089265&lang=fa&units=metric").build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("tagx","download falid!!")
            }

            override fun onResponse(call: Call, response: Response) {
                val rowresponse = response.body!!.string()
                getFromServer(rowresponse)

            }

        })
    }


    private fun getData_tehran(){
        var client = OkHttpClient()
        var request = Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?q=tehran&appid=8e2c4853730bbc1a089bdbe682089265&lang=fa&units=metric").build()

        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("tagx","download falid!!")
            }

            override fun onResponse(call: Call, response: Response) {
                val rowresponse = response.body!!.string()
                getFromServer(rowresponse)

            }

        })
    }



    private fun getFromServer(Datain:String){
        val responseJson = JSONObject(Datain)
        //weather array
        val weatherArray = responseJson.getJSONArray("weather")
        val zeroweather = weatherArray.getJSONObject(0)
        val IconId = zeroweather.getString("icon")
        val IconImage  = "https://openweathermap.org/img/wn/${IconId}@2x.png"
        //sun rise/set
        val sysObject = responseJson.getJSONObject("sys")
        val sunRise = sysObject.getInt("sunrise")
        val sunSet = sysObject.getInt("sunset")
        //main
        val main_temp = responseJson.getJSONObject("main")
        val feelsLike = main_temp.getDouble("feels_like")
        val temp_min = main_temp.getDouble("temp_min")
        val temp_max = main_temp.getDouble("temp_max")
        val pressure = main_temp.getInt("pressure")
        val humidity = main_temp.getDouble("humidity")
        //wind
        val wind = responseJson.getJSONObject("wind")
        val Speed = wind.getDouble("speed")
        val Digree = wind.getInt("deg")

        runOnUiThread {
            sets(responseJson.getString("name"),
                zeroweather.getString("description"),
                main_temp.getDouble("temp"),
                IconImage,
                sunRise,sunSet,
                feelsLike,
                temp_max,
                temp_min,
                pressure,
                humidity,
                Speed,
                Digree)
        }
    }




    fun ReloadBar(veiw:View){
        binding.CityBar.visibility = View.INVISIBLE
        binding.imageView.visibility =  View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.textViewcityname.text = "--"
        binding.textViewWeather.text = "--"
        binding.textViewtemp.text = "--"
        binding.textViewsunRise.text = "--"
        binding.textViewSunSet.text = "--"
        binding.textViewtempMin.text = "--"
        binding.textViewtempMax.text = "--"
        binding.textViewfeelsLike.text = "--"
        binding.textViewpressure.text = "--"
        binding.textViewhumidity.text = "--"
        binding.textViewDegri.text = "--"
        binding.textViewWind.text = "--"
        Glide.with(this@MainActivity).load(R.drawable.ic_refresh).into(binding.imageViewphoto)
        getData_tehran()
    }

    fun SimpleReload(){
        binding.imageView.visibility =  View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.textViewcityname.text = "--"
        binding.textViewWeather.text = "--"
        binding.textViewtemp.text = "--"
        binding.textViewsunRise.text = "--"
        binding.textViewSunSet.text = "--"
        binding.textViewtempMin.text = "--"
        binding.textViewtempMax.text = "--"
        binding.textViewfeelsLike.text = "--"
        binding.textViewpressure.text = "--"
        binding.textViewhumidity.text = "--"
        binding.textViewDegri.text = "--"
        binding.textViewWind.text = "--"
        Glide.with(this@MainActivity).load(R.drawable.ic_refresh).into(binding.imageViewphoto)
    }

    fun Bot_bush(view:View){
        SimpleReload()
        getData_bush()
        binding.CityBar.visibility = View.INVISIBLE
    }



    fun Bot_Tab(view:View){
        SimpleReload()
        getData_tab()
        binding.CityBar.visibility = View.INVISIBLE
    }



    fun Bot_Mash(view:View){
        SimpleReload()
        getData_mash()
        binding.CityBar.visibility = View.INVISIBLE
    }

    fun ToolBarAcc(view:View){
        binding.CityBar.visibility = View.VISIBLE
    }

    fun clickonnull(view : View ){
        binding.CityBar.visibility = View.INVISIBLE
    }


}