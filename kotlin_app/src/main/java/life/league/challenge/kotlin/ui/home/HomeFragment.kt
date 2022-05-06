package life.league.challenge.kotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.databinding.FragmentHomeBinding
import life.league.challenge.kotlin.model.Result
import life.league.challenge.kotlin.ui.adapter.users.UsersAdapter
import life.league.challenge.kotlin.ui.base.BaseFragment
import life.league.challenge.kotlin.utils.extentions.hideProgress
import life.league.challenge.kotlin.utils.extentions.showProgress
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val mHomeViewModel: HomeViewModel by viewModels()
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mAdapter: UsersAdapter
    private var mJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding.homeViewModel = mHomeViewModel
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun setupView() {
        mAdapter = UsersAdapter(onItemClickListener = {
            Timber.i(" onItemClickListener: ${it.username}")
            it.id?.let { userID -> navigateToPostsFragment(userID) }
        })
        mHomeViewModel.login()
        setupAdapter()
    }

    override fun setupUiListener() {
        mBinding.swipeRefreshLayoutHomeFragment.setOnRefreshListener {
            handleUsersCall()
        }
    }

    override fun setupObservers() {
        mHomeViewModel.mLogin.observe(viewLifecycleOwner, {
            when (it.status) {
                Result.Status.LOADING -> showProgress()
                Result.Status.ERROR -> {
                    hideProgress()
                    showEmptyList(true)
                }
                Result.Status.SUCCESS -> {
                    hideProgress()
                    handleUsersCall()
                }
            }
        })

    }

    private fun handleUsersCall() {
        mBinding.swipeRefreshLayoutHomeFragment.isRefreshing = false
        if (mHomeViewModel.checkNetworkConnection()) {
            getAllUser()
            showEmptyList(false)
        } else {
            Toast.makeText(
                requireContext(),
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT
            ).show()
            showEmptyList(true)
        }
    }

    private fun getAllUser() {
        mJob?.cancel()
        mJob = lifecycleScope.launch {
            mHomeViewModel.getAllUser().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        mBinding.recyclerViewHomeFragment.adapter = mAdapter
    }

    private fun navigateToPostsFragment(
        userId: Int
    ) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                userId
            )
        findNavController().navigate(action)
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            mBinding.textViewHomeFragmentError.visibility = View.VISIBLE
            mBinding.recyclerViewHomeFragment.visibility = View.GONE
        } else {
            mBinding.textViewHomeFragmentError.visibility = View.GONE
            mBinding.recyclerViewHomeFragment.visibility = View.VISIBLE
        }
    }
}