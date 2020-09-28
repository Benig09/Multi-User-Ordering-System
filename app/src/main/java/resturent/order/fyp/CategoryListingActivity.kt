package resturent.order.fyp

import android.os.Bundle
import android.view.View
import resturent.order.fyp.models.Product
import resturent.order.fyp.utils.extensions.addProduct
import resturent.order.fyp.utils.extensions.loadImage
import resturent.order.fyp.utils.extensions.setVerticalLayout
import kotlinx.android.synthetic.main.category_activity_listing.*
import kotlinx.android.synthetic.main.category_item_card.view.*
import kotlinx.android.synthetic.main.category_toolbar.*
import kotlinx.android.synthetic.main.category_toolbar_below.*

class CategoryListingActivity : CategoryBaseActivity() {

    private lateinit var listAdapter: RecyclerViewAdapter<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity_listing_old)
        setHomeNavigation()

        toolBarTitle.text="Select Category"
        toolBarTitle2.text="Select Category"

         listAdapter = RecyclerViewAdapter<Product>(
            R.layout.category_item_card,
            onBind = { view: View, product: Product, i: Int ->
                loadImage(product.img!!,view.ivProduct)
                view.tvProductName.text = product.name

            }

         )
        rvList.apply {
            setVerticalLayout()
            adapter = listAdapter
        }



        rvList2.apply {
            setVerticalLayout()
            adapter = listAdapter
        }


        listAdapter.addItems(getProducts())




    }

    private fun addQuantity(product: Product, i: Int, qty: Int) {
        if ((product.qty+qty)<10 && (product.qty+qty)>0){
            product.qty=product.qty+qty
            listAdapter.notifyItemInserted(i)
        }

    }
    private fun getProducts(): List<Product> {
        var list=ArrayList<Product>()
        list.apply {
            addProduct { name = "Food"; img = R.drawable.food; price="0";qua="$100";category="By Boots Category" }
            addProduct { name = "Drink"; img = R.drawable.food;price="$50";qua="$100";category="By Headset Category" }
            addProduct { name = "Beverage"; img = R.drawable.food; price="$20";qua="$30";category="By Pots Category" }
            addProduct { name = "snack"; img = R.drawable.food; price="$550";qua="$750";category="By Watch Category" }
        }
        return list
    }

}
