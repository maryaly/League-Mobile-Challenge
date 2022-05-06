package life.league.challenge.kotlin.utils.extentions

import androidx.fragment.app.Fragment
import life.league.challenge.kotlin.ui.MainActivity


fun Fragment.showProgress() {
    activity?.let {
        (it as MainActivity).showProgress()
    }
}

fun Fragment.hideProgress() {
    activity?.let {
        (it as MainActivity).hideProgress()
    }
}