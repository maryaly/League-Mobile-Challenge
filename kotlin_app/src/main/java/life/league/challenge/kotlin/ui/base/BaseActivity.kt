package life.league.challenge.kotlin.ui.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUiListener()
        setupUiComponent()
        setupObservers()
    }

    protected abstract fun setupUiListener()
    protected abstract fun setupUiComponent()
    protected abstract fun setupObservers()
}