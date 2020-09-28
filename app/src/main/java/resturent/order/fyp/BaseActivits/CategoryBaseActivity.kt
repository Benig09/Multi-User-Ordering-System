package resturent.order.fyp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import resturent.order.fyp.utils.extensions.color
import resturent.order.fyp.utils.extensions.makeNormalStatusBar
import resturent.order.fyp.utils.extensions.onClick
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.category_toolbar.*


open class CategoryBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeNormalStatusBar(color(R.color.theme10_layout_background_white))
    }
    fun setHomeNavigation() {
        ivBack.onClick {
            onBackPressed()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}