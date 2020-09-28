package resturent.order.fyp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import resturent.order.fyp.utils.extensions.launchActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchActivity<Main2Activity>();

        onBackPressed()
    }
}
