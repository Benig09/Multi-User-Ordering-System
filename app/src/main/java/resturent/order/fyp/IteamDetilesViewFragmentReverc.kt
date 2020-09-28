package resturent.order.fyp


import android.content.Context
import android.os.Bundle
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.category_toolbar.view.*
import kotlinx.android.synthetic.main.item_detils_card.view.*
import resturent.order.fyp.utils.extensions.onClick

class IteamDetilesViewFragmentReverc : Fragment() {

    private var listener4: FragmentDListener? = null

    private val arrPackage = ArrayList<String>()

    interface FragmentDListener {
        fun onInputDSent(input: String)




    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        arrPackage.clear()
        Log.e("fdd: ", "jhjkhgfhdhd")

        val arrPackageData22 = GetPrefrenceData()


        if (arrPackageData22 != null) {
            for (data in arrPackageData22) {


                val productName = data

                arrPackage?.add(productName)





            }
        }

        val input = arguments?.getString("index")

        var delimiter = "--"

        var parts: Array<String> = input!!.split(delimiter).toTypedArray()

        var nameeZ = parts[1]
        var descriptionZ = parts[2]
        var imgZ = parts[3]
        var PriceZ = parts[4]
        var waitingTimeZ = parts[5]
        var ITeamID = parts[6]

        // todo end to delete
       val v = inflater.inflate(R.layout.item_detils_card_reverc, container, false)

        v.ivSort.isVisible = false
        v.toolBarTitle.text="Iteam Deatils"
        v.tvProductName.text= nameeZ
        v.tvDiscountPrice2.text= PriceZ!!.toFloat().toString()
        v.tvDiscountPrice3.text= waitingTimeZ
        v.tvDescription.text= descriptionZ
        loadImage(imgZ!!.toInt(),v.ivProduct)

        val arrPackageData2 = GetPrefrenceData()
        var Delete = "0"

        if (arrPackageData2 != null) {
            for (data in arrPackageData2) {

                if (data == nameeZ.toString()){


                    Delete = "1"
                    break
                }


            }
        }

        if ( Delete == "1" ){

            v.ddcvvz.setBackgroundResource(R.color.arrow_color)


        }



        v.ivBack.onClick {

            val input = "6"
           listener4?.onInputDSent(input)
        }

        v.ivCart.setOnClickListener {
            val input = "5"
            listener4?.onInputDSent(input)
        }



        v.ivAdd.setOnClickListener {

            val productName = nameeZ
            val productPrice = PriceZ
            val productImage = imgZ



            if (Delete.equals("0")){


                arrPackage?.add(productName)
                arrPackage?.add(productPrice)
                arrPackage?.add("1")
                arrPackage?.add(productImage)
                arrPackage?.add(ITeamID)
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

                v.ddcvvz.setBackgroundResource(R.color.arrow_color)

                Delete = "1"

            } else {


                arrPackage?.remove(productName)
                arrPackage?.remove(productPrice)
                arrPackage?.remove("1")
                arrPackage?.remove(productImage)

                val preferences = this.activity!!.getSharedPreferences("pref2", Context.MODE_PRIVATE)
                preferences.edit().clear().commit()
                val gson = Gson()
                val json = gson.toJson(arrPackage)

                with (preferences.edit()) {
                    putString("Set", json)
                    commit()
                }

                Toast.makeText(activity,productName + " Removed from cart", Toast.LENGTH_LONG).show()


                v.ddcvvz.setBackgroundResource(R.color.theme10_white)

                Delete = "0"

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