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
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.category_toolbar.view.*

import kotlinx.android.synthetic.main.item_item_card.view.*
import kotlinx.android.synthetic.main.item_view_fragment.view.*
import org.json.JSONException
import org.json.JSONObject


import resturent.order.fyp.models.Product
import resturent.order.fyp.utils.extensions.addProduct
import resturent.order.fyp.utils.extensions.onClick
import resturent.order.fyp.utils.extensions.setVerticalLayout
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class IteamViewFragmentReverc : Fragment() {

    private var listener4: FragmentDListener? = null
    private var arrPackage = ArrayList<String>()
    private var sortAsc = true
    private var MEnuIDD = "ba859985-7663-485f-a829-9f182c3fcb7c"


    private lateinit var listener: RecyclerViewAdapter<Product>

    interface FragmentDListener {
        fun onInputDSent(input: String)


    }

    private var listAdapter = RecyclerViewAdapter<Product>(R.layout.item_item_card, onBind = { view: View, people: Product, i: Int ->




        view.tvDiscountPrice2.text = people.price!!.toFloat().toString()
        view.tvDiscountPrice3.text = people.waitingtime
        loadImage(people.img!!,view.ivProduct)
        view.tvProductName.text = people.name

        view.tvProductName.setOnClickListener {



            var str = "IteamDetilesReverc"+"--"+
                    people.name + "--" +
                    people.description+ "--" +
                    people.img + "--" +
                    people.price+ "--" +
                    people.waitingtime + "--"
                    people.id + "--"

            val input = str
            listener4?.onInputDSent(input)



        }

        val arrPackageData2 = GetPrefrenceData()
        var Delete = "0"

        if (arrPackageData2 != null) {
            for (data in arrPackageData2) {

                if (data == people.name.toString()){


                    Delete = "1"
                    break
                }


            }
        }

        if ( Delete == "1" ){




            view.setBackgroundResource(R.color.arrow_color)




        }









        view.AddtoCart.setOnClickListener {

            val productName = people.name.toString()
            val productPrice = people.price.toString()
            val productImage = people.img.toString()
            val productqua = people.qua.toString()
            val productID = people.id.toString()



            if (Delete.equals("0")){




                arrPackage?.add(productName)
                arrPackage?.add(productPrice)
                arrPackage?.add(productqua)
                arrPackage?.add(productImage)
                arrPackage?.add(productID)
                //
                Log.e("arry : ",arrPackage.toString())


                val preferences = this.activity!!.getSharedPreferences("pref2", Context.MODE_PRIVATE)
                val gson = Gson()
                val json = gson.toJson(arrPackage)

                with (preferences.edit()) {
                    putString("Set", json)
                    commit()
                }



                Toast.makeText(activity,productName + " Add to cart", Toast.LENGTH_LONG).show()

                view.setBackgroundResource(R.color.arrow_color)

                Delete = "1"

            } else {


                var x = 0

                var arrPackage223 = ArrayList<String>()

                arrPackage223.clear()

                if (arrPackage != null) {
                    for (data in arrPackage) {




                        if (x==5){

                            x=0
                        }



                        if (x==4){

                            x=5
                        }

                        if (x==3){


                            x =4
                        }



                        if (x == 2){



                            x = 3
                        }

                        if (x == 1){

                            x = 2
                        }

                        if (data == people.name) {

                            x = 1

                        }else if (x==0) {

                            arrPackage223.add(data)
                        }











                    }
                }

                arrPackage.clear()
                arrPackage =   arrPackage223



                val preferences = this.activity!!.getSharedPreferences("pref2", Context.MODE_PRIVATE)
                preferences.edit().clear().commit()
                val gson = Gson()
                val json = gson.toJson(arrPackage)

                with (preferences.edit()) {
                    putString("Set", json)
                    commit()
                }

                Toast.makeText(activity,productName + " Removed from cart", Toast.LENGTH_LONG).show()

                view.setBackgroundResource(R.color.theme10_white)

                Delete = "0"

            }
        }





    })


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        arrPackage.clear()

        MEnuIDD = arguments?.getString("index").toString()

        val v = inflater.inflate(R.layout.item_view_fragment, container, false)


        v.toolBarTitle.text="Menu"

        v.ivBack.onClick {

            val input = "4"
            listener4?.onInputDSent(input) // send data
        }


        v.rvListz.setVerticalLayout()


        v.rvListz.adapter = listAdapter
        var list=ArrayList<Product>()

        class LoadMenuItem :
            AsyncTask<String, Void?, String?>() {
            override fun doInBackground(params: Array<String>): String? {


                // HTTPPOST to API
                var response: String? = null
                var url: URL? = null
                try {
                    url =
                        URL("https://fyp.amazecraft.net/Api/MenuItem/RetrieveListByMenuId")
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
                    json.put("MenuId", MEnuIDD)
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
                        val jArray = obj.getJSONArray("menuItemList")
                        list.clear()
                        for (i in 0 until jArray.length()) {
                            val tmpObj = jArray.getJSONObject(i)



                            //  Log.e("4",jArray.toString())

                            newMenuItem.id = (tmpObj.getString("id"))
                            newMenuItem.name = (tmpObj.getString("name"))
                            newMenuItem.description = (tmpObj.getString("shortDesc"))
                            newMenuItem.price = (tmpObj.getString("price"))


                            newMenuItem.img  = R.drawable.food
                            newMenuItem.qua  = "1"

                            var waitingTime = i + 2

                            newMenuItem.waitingtime = waitingTime.toString() + " Mint"

                            list.add(newMenuItem)
                            newMenuItem = Product()
                        }
                        newMenuItem.Menuname = (obj.getString("menuName"))
                        newMenuItem.MenunId = (obj.getString("menuId"))

                        Log.e("5",jArray.toString())
                        Log.e("6",(obj.getString("menuId")))
                        0

                        listAdapter.addItems(list)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            }
        }

        LoadMenuItem().execute()



        v.ivCart.setOnClickListener {
            val input = "5"
            listener4?.onInputDSent(input)
        }

        var handller = true
        v.ivSort.setOnClickListener {

            if (handller) {
                //  val sortedList = list.sortedWith(compareBy(Product::price, Product::name))
                list.sortBy{it.price}
                listAdapter.clearItems()
                listAdapter.addItems(list)
                listAdapter.notifyDataSetChanged()

                Toast.makeText(activity, "Sorted by Price",Toast.LENGTH_LONG).show()

                handller = false

            }else {

                //  val sortedList = getProducts().sortedWith(compareBy(Product::waitingtime, Product::waitingtime))
                list.sortBy{it.waitingtime}
                listAdapter.clearItems()
                listAdapter.addItems(list)
                listAdapter.notifyDataSetChanged()
                Toast.makeText(activity, "Sorted by Waiting Time ",Toast.LENGTH_LONG).show()
                handller = true
            }

        }






        val arrPackageData22 = GetPrefrenceData()


        if (arrPackageData22 != null) {
            for (data in arrPackageData22) {


                val productName = data
                arrPackage?.add(productName)

            }
        }



        return v
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentDListener) {
            listener4 = context as Main2Activity
        } else {
            throw RuntimeException("$context must implement FragmentBListener")
        }
    }

    private fun getProducts(): List<Product> {
        var list=ArrayList<Product>()
        list.apply {
            addProduct { name = "Orange juice"; img = R.drawable.juice4; price="7 RM";qua="1";category="false";waitingtime="9 Mint";description="It is a very clear liquid from which the pulp has been removed.."}
            addProduct { name = "Apple juice"; img = R.drawable.juice2;price="9 RM";qua="1";category="false";waitingtime="5 Mint";description="It is a very clear liquid from which the pulp has been removed.." }
            addProduct { name = "Tomato juice"; img = R.drawable.juice3; price="5 RM";qua="1";category="false";waitingtime="8 Mint";description="It is a very clear liquid from which the pulp has been removed.." }
            addProduct { name = "Mix juice"; img = R.drawable.juice1; price="8 RM";qua="1";category="false";waitingtime="3 Mint";description="It is a very clear liquid from which the pulp has been removed.." }
        }
        return list
    }

    fun loadImage(image: Int, imageView: ImageView) {
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
    }









    private fun GetPrefrenceData(): List<String>? {

        val preferences = this.activity!!.getSharedPreferences("pref2", Context.MODE_PRIVATE)

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




}