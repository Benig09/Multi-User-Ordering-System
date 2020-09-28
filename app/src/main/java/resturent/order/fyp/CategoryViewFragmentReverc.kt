package resturent.order.fyp


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.category_toolbar.view.*
import kotlinx.android.synthetic.main.item_item_card.view.*
import kotlinx.android.synthetic.main.item_view_fragment.view.*
import org.json.JSONException
import org.json.JSONObject


import resturent.order.fyp.models.Product
import resturent.order.fyp.utils.extensions.addProduct

import resturent.order.fyp.utils.extensions.setVerticalLayout
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class CategoryViewFragmentReverc : Fragment() {

    private var listener3: FragmentBListener? = null

    private lateinit var listener: RecyclerViewAdapter<Product>

     interface FragmentBListener {

        fun onInputBSent(input: CharSequence)


    }


    private var listAdapter = RecyclerViewAdapter<Product>(R.layout.item_item_card, onBind = { view: View, people: Product, i: Int ->



        loadImage(people.img!!,view.ivProduct)
        view.tvProductName.text = people.name

        view.tvProductName.setOnClickListener {
            val input = people.id.toString()
            listener3?.onInputBSent(input)
        }

    })


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.category_view_fragment, container, false)


        v.toolBarTitle.text="Select Category"
        v.ivSort.isVisible = false

        v.rvListz.setVerticalLayout()


        v.rvListz.adapter = listAdapter
        var list=ArrayList<Product>()



        class LoadMenu :
            AsyncTask<String?, Void?, String?>() {
            override fun doInBackground(params: Array<String?>): String? {

                // HTTPPOST to API
                var response: String? = null
                var url: URL? = null
                try {
                    url =
                        URL("https://fyp.amazecraft.net/Api/Menu/RetrieveListByVendorId")
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
                    json.put("VendorId", "e916642b-d464-476f-920d-43462d0110b3")
                    val wr =
                        OutputStreamWriter(con.outputStream)
                    wr.write(json.toString())
                    wr.flush()
                    BufferedReader(
                        InputStreamReader(
                            con.inputStream,
                            "utf-8"
                        )
                    ).use { br ->
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
                return response

            }

            override fun onPostExecute(message: String?) {
                if (message != null) {
                    try {


                        var newMenuItem = Product()

                        val obj = JSONObject(message)
                        val jArray = obj.getJSONArray("menuList")
                        for (i in 0 until jArray.length()) {
                            val tmpObj = jArray.getJSONObject(i)
                            Log.e("1 : ",tmpObj.getString("id"))
                            Log.e("2",tmpObj.getString("name"))
                            Log.e("2Arry ",jArray.toString())

                            newMenuItem.id = (tmpObj.getString("id"))
                            newMenuItem.name = (tmpObj.getString("name"))


                            newMenuItem.img = R.drawable.food


                            list.add(newMenuItem)
                            newMenuItem = Product()
                        }
                       listAdapter.addItems(list)


                        obj["menuList"]
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            }
        }

        LoadMenu().execute()

       // listAdapter.addItems(list)


        v.ivCart.setOnClickListener {
            val input = "2"
            listener3?.onInputBSent(input)
        }

        return v
    }



    fun updateEditText(newText: CharSequence) {



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentBListener) {
            listener3 = context as Main2Activity
        } else {
            throw RuntimeException("$context must implement FragmentBListener")
        }
    }





    fun loadImage(image: Int, imageView: ImageView) {
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
    }






}