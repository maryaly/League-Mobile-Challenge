package life.league.challenge.kotlin.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

    }

    fun showProgress() {
        mBinding.mainActivityLoading.visibility = View.VISIBLE
    }

    fun hideProgress() {
        mBinding.mainActivityLoading.visibility = View.INVISIBLE
    }


}
