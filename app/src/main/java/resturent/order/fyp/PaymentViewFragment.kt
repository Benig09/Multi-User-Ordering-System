package resturent.order.fyp


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.category_toolbar.view.*
import kotlinx.android.synthetic.main.item_payment_card.view.*
import org.json.JSONException
import org.json.JSONObject
import resturent.order.fyp.models.Product
import resturent.order.fyp.utils.extensions.onClick
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class PaymentViewFragment : Fragment() {

    private var listener4: FragmentCListener? = null

    private val arrPackage = ArrayList<String>()
    var OrderID = "0"

    interface FragmentCListener {
        fun onInputCSent(input: String)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        val TotalPRice = arguments?.getString("index")


        // todo end to delete
       val v = inflater.inflate(R.layout.item_payment_card, container, false)

        v.ivSort.isVisible = false
        v.ivCart.isVisible = false
        v.toolBarTitle.text="Total : " + TotalPRice




        v.ivBack.onClick {

            val input = "8"
           listener4?.onInputCSent(input)
        }


        v.imageView2.onClick {


            sendCart()


        }

        v.imageView3.onClick {

            sendCart()

        }


        v.Cash.onClick {

            sendCart()

        }







        return v
    }

    private fun getProducts(): List<Product> {
        var list=ArrayList<Product>()



        val arrPackageData22 = GetPrefrenceData()




        var x = 0


        var model3 = Product()


        if (arrPackageData22 != null) {
            for (data in arrPackageData22) {

                if (x==0){

                    Log.e("ddd0", data)
                    model3.name = data
                    x=1

                }else if (x==1){

                    model3.price = data
                    Log.e("ddd1", data)

                    x=2

                }else if (x==2){
                    Log.e("ddd2", data)

                    model3.qua = data  // quantity

                    x = 3
                }else if (x==3){
                    Log.e("ddd2", data)

                    model3.img = data.toInt() // quantity

                    x = 4
                }else if (x==4){


                    model3.id = data
                    model3.category = "false"  // dump data

                    list.add(model3)
                    model3 = Product()
                    x=0

                }










                val productName = data

                arrPackage?.add(productName)




            }
        }



        return list
    }


    private fun GetPrefrenceData(): List<String>? {

        val preferences = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var list=ArrayList<Product>()
        val gson = Gson()
        val json = preferences.getString("Set", "")

        val type = object : TypeToken<List<String>>() {

        }.type



        val arrPackageData = gson.fromJson<List<String>>(json,type)

        if(arrPackageData!=null && arrPackageData.size>0) {
            return arrPackageData
        }

        return null

    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCListener) {
            listener4 = context as Main2Activity
        } else {
            throw RuntimeException("$context must implement FragmentBListener")
        }
    }


    fun loadImage(image: Int, imageView: ImageView) {
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
    }

    fun sendCart() {

        // post data

        class LoadMenuItem :
            AsyncTask<String, Void?, String?>() {
            override fun doInBackground(params: Array<String>): String? {


                // HTTPPOST to API
                var response: String? = null
                var url: URL? = null
                try {
                    url =
                        URL("https://fyp.amazecraft.net/Api/User/CreateTempUserSession")
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
                try {
                    val con =
                        url!!.openConnection() as HttpURLConnection
                    con.requestMethod = "POST"
                    con.setRequestProperty("Content-Type", "application/json")
                    con.setRequestProperty("Accept", "application/json")

                    BufferedReader(InputStreamReader(con.inputStream, "utf-8"))
                        .use { br ->
                            val sb = StringBuilder()
                            var responseLine: String? = null
                            while (br.readLine().also { responseLine = it } != null) {
                                sb.append(responseLine!!.trim { it <= ' ' })
                            }
                            response = sb.toString()
                        }
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: NetworkOnMainThreadException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                return  response

            }

            override fun onPostExecute(message: String?) {
                if (message != null) {
                    try {


                        var newMenuItem = Product()

                        val obj = JSONObject(message)

                        var SessionID =     obj.getString("sessionId").toString()
                        var SessionKey =     obj.getString("sessionKey").toString()

                        // new call

                        Log.e("seesion ", SessionID)
                        Log.e("seesion Key : ", SessionKey)






                        ////////////////////////////////////////////

                        // Create order id

                        class LoadMenuItem2 :
                            AsyncTask<String, Void?, String?>() {
                            override fun doInBackground(params: Array<String>): String? {


                                // HTTPPOST to API
                                var response: String? = null
                                var url: URL? = null
                                try {
                                    url =
                                        URL("https://fyp.amazecraft.net/Api/Order/Create")
                                } catch (e: MalformedURLException) {
                                    e.printStackTrace()
                                }
                                try {
                                    val con =
                                        url!!.openConnection() as HttpURLConnection
                                    con.requestMethod = "POST"
                                    con.setRequestProperty("Content-Type", "application/json")
                                    con.setRequestProperty("Accept", "application/json")
                                    con.doInput = true


                                    val json = JSONObject()
                                    json.put("type", 2)
                                    json.put("sessionId", SessionID)
                                    json.put("sessionKey", SessionKey)
                                    json.put("vendorId","e916642b-d464-476f-920d-43462d0110b3")

                                    val wr =
                                        OutputStreamWriter(con.outputStream)
                                    wr.write(json.toString())
                                    wr.flush()



                                    BufferedReader(InputStreamReader(con.inputStream, "utf-8"))
                                        .use { br ->
                                            val sb = StringBuilder()
                                            var responseLine: String? = null
                                            while (br.readLine().also { responseLine = it } != null) {
                                                sb.append(responseLine!!.trim { it <= ' ' })
                                            }
                                            response = sb.toString()

                                            val obj = JSONObject(response)
                                            OrderID = obj.getString("orderId") // get the order id

                                            Log.e("OrderID ", OrderID)

                                        }
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                } catch (e: NetworkOnMainThreadException) {
                                    e.printStackTrace()
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                                return  response

                            }

                            override fun onPostExecute(message: String?) {


// submit orders

                                ////////////////////////////////////

                                ////////////////////////////////////////////

                                // Create order id

                                class LoadMenuItem3 :
                                    AsyncTask<String, Void?, String?>() {
                                    override fun doInBackground(params: Array<String>): String? {

                                        var list= getProducts()

                                        var orderMenuItems =
                                            HashSet<JSONObject>()

                                        for (item in list) {
                                            val newObj = JSONObject()
                                            try {

                                                newObj.put(
                                                    "itemId",
                                                    item.id
                                                )
                                                newObj.put("quantity", item.qua)

                                                orderMenuItems.add(newObj)

                                            } catch (e: JSONException) {
                                                e.printStackTrace()
                                            }
                                        }





                                        // HTTPPOST to API
                                        var response: String? = null
                                        var url: URL? = null
                                        try {
                                            url =
                                                URL("https://fyp.amazecraft.net/Api/Order/AddItemByOrderIdSeparate")
                                        } catch (e: MalformedURLException) {
                                            e.printStackTrace()
                                        }
                                        try {
                                            val con =
                                                url!!.openConnection() as HttpURLConnection
                                            con.requestMethod = "POST"
                                            con.setRequestProperty("Content-Type", "application/json")
                                            con.setRequestProperty("Accept", "application/json")
                                            con.doInput = true


                                            val json = JSONObject()

                                            json.put("orderId",  OrderID)
                                            json.put("items", orderMenuItems)


                                            val wr =
                                                OutputStreamWriter(con.outputStream)
                                            val newJson =
                                                json.toString().replace("\"[", "[")
                                                    .replace("]\"", "]").replace("\\\"", "\"")
                                            wr.write(newJson)
                                            wr.flush()



                                            BufferedReader(InputStreamReader(con.inputStream, "utf-8"))
                                                .use { br ->
                                                    val sb = StringBuilder()
                                                    var responseLine: String? = null
                                                    while (br.readLine().also { responseLine = it } != null) {
                                                        sb.append(responseLine!!.trim { it <= ' ' })
                                                    }
                                                    response = sb.toString()



                                                }
                                        } catch (e: IOException) {
                                            e.printStackTrace()
                                        } catch (e: NetworkOnMainThreadException) {
                                            e.printStackTrace()
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                        return  response

                                    }

                                    override fun onPostExecute(message: String?) {

                                        try {
                                            val obj = JSONObject(message)
                                            val status = obj.getString("status")
                                            if (status == "OK") {

                                                val settings = context!!.getSharedPreferences(
                                                    "pref",
                                                    Context.MODE_PRIVATE
                                                )
                                                settings.edit().clear().commit()

                                                Toast.makeText(activity, "Order is submitted Successfully", Toast.LENGTH_LONG).show()

                                                val input = "6"
                                                listener4?.onInputCSent(input)

                                            } else {


                                                Toast.makeText(activity, "There Was Error Posting to Server", Toast.LENGTH_LONG).show()


                                            }
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }


                                    }
                                }


                                ////////////////////////////////////
                                LoadMenuItem3().execute()
                            }
                        }




                        ////////////////////////////////////////////



                        LoadMenuItem2().execute()





                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            }
        }

        LoadMenuItem().execute()

        // Clear Cart


    }











}