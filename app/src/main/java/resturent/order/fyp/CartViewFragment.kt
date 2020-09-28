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
import kotlinx.android.synthetic.main.category_toolbar.view.*
import kotlinx.android.synthetic.main.item_item_card.view.*
import kotlinx.android.synthetic.main.item_view_fragment.view.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.cart_item_card.view.*
import kotlinx.android.synthetic.main.category_toolbar.*
import kotlinx.android.synthetic.main.category_toolbar.view.toolBarTitle
import kotlinx.android.synthetic.main.item_item_card.view.ivProduct
import kotlinx.android.synthetic.main.item_item_card.view.tvDiscountPrice2
import kotlinx.android.synthetic.main.item_item_card.view.tvProductName
import resturent.order.fyp.models.Product
import resturent.order.fyp.utils.extensions.onClick
import resturent.order.fyp.utils.extensions.setVerticalLayout




class CartViewFragment : Fragment() {

    private var listener4: FragmentCListener? = null
    private val arrPackage = ArrayList<String>()
    private var TotalPrice = 0.0


    private lateinit var listener: RecyclerViewAdapter<Product>

    interface FragmentCListener {
        fun onInputCSent(input: String)


    }

    private var listAdapter = RecyclerViewAdapter<Product>(R.layout.cart_item_card, onBind = { view: View, people: Product, i: Int ->

        view.tvDiscountPrice2.text = people.price!!.toFloat().toString()

        view.tvQuaCounter.text = people.qua
        people.img?.let { loadImage(it,view.ivProduct) }
        view.tvProductName.text = people.name

        TotalPrice =  TotalPrice + (people.qua!!.toDouble() * people.price!!.toDouble())

       toolBarTitle.text="Total : " + TotalPrice!!.toFloat().toString()



        var counter = people.qua?.toInt()

        view.ivAdd_cart.setOnClickListener {





            counter = counter?.plus(1)


            TotalPrice =  TotalPrice + (1.0 * people.price!!.toDouble())

            toolBarTitle.text="Total : " + TotalPrice.toString()

            view.tvQuaCounter.text = counter.toString()

            val arrPackageData223 = GetPrefrenceData()

            var x = 0

             var arrPackage223 = ArrayList<String>()

            arrPackage223.clear()

            if (arrPackageData223 != null) {
                for (data in arrPackageData223) {



                    if (x == 2){

                        arrPackage223.add(counter.toString())

                        x = 3
                    }else {

                        arrPackage223.add(data)
                    }

                    if (x == 1){

                        x = 2
                    }



                    if (data == people.name) {

                        x = 1

                    }




                }
            }






            val preferences = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = gson.toJson(arrPackage223)

            with (preferences.edit()) {
                putString("Set", json)
                commit()
            }



        }

        view.ivMins.setOnClickListener {



            if (counter ==1){


            }else {

                counter = counter?.minus(1)

                TotalPrice =  TotalPrice - (1.0 * people.price!!.toDouble())

                toolBarTitle.text="Total : " + TotalPrice.toString()

                view.tvQuaCounter.text = counter.toString()

                val arrPackageData223 = GetPrefrenceData()

                var x = 0

                var arrPackage223 = ArrayList<String>()

                arrPackage223.clear()

                if (arrPackageData223 != null) {
                    for (data in arrPackageData223) {



                        if (x == 2){

                            arrPackage223.add(counter.toString())

                            x = 3
                        }else {

                            arrPackage223.add(data)
                        }

                        if (x == 1){

                            x = 2
                        }



                        if (data == people.name) {

                            x = 1

                        }




                    }
                }






                val preferences = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
                val gson = Gson()
                val json = gson.toJson(arrPackage223)

                with (preferences.edit()) {
                    putString("Set", json)
                    commit()
                }
            }





        }


    })


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.item_view_fragment, container, false)




        v.ivSort.setImageResource(R.drawable.mastercard);
        v.rvListz.setVerticalLayout()


        v.rvListz.adapter = listAdapter

        val args = arguments

        val index = args!!.getCharSequence("index", "xx") // Recived Argment


        listAdapter.addItems(getProducts())
        //  ivClose.onClick { activity!!.onBackPressed() }

        v.ivBack.onClick {

            val input = "4" // data to next Activity
            listener4?.onInputCSent(input) // send data
        }

        v.ivCart.setOnClickListener {
            val input = "6"
            listener4?.onInputCSent(input)
        }


        v.ivSort.setOnClickListener {
            val input = "1"+"--"+TotalPrice.toString()// open PaymentActivity ////////////////
            listener4?.onInputCSent(input)
        }

        return v
    }



    fun updateEditText(newText: String) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCListener) {
            listener4 = context as Main2Activity
        } else {
            throw RuntimeException("$context must implement FragmentBListener")
        }
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









                // to fill up the arrPackage

                val productName = data

                arrPackage?.add(productName)




                //Toast.makeText(activity, data, Toast.LENGTH_LONG).show()
            }
        }



       // list.apply {
           // addProduct { name = "Orange juice"; img = R.drawable.juice4; price="RM 10";qua="$100";category="By Boots Category" }
           /// addProduct { name = "Apple juice"; img = R.drawable.juice2;price="RM 10";qua="$100";category="By Headset Category" }
           // addProduct { name = "Tomato juice"; img = R.drawable.juice3; price="RM 4.5";qua="$30";category="By Pots Category" }
           // addProduct { name = "Mix juice"; img = R.drawable.juice1; price="RM 12.40";qua="$750";category="By Watch Category" }
      //  }
        return list
    }

    fun loadImage(image: Int, imageView: ImageView) {
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
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




}