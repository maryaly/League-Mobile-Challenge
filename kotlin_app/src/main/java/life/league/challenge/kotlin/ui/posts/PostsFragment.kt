package life.league.challenge.kotlin.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.databinding.FragmentPostsBinding
import life.league.challenge.kotlin.ui.adapter.posts.PostsAdapter
import life.league.challenge.kotlin.ui.adapter.users.UsersAdapter
import life.league.challenge.kotlin.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class PostsFragment : BaseFragment() {

    private val mPostsViewModel: PostsViewModel by viewModels()
    private lateinit var mBinding: FragmentPostsBinding
    private val mArgs: PostsFragmentArgs by navArgs()
    private lateinit var mAdapter: PostsAdapter
    private var mJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPostsBinding.inflate(inflater, container, false)
        mBinding.postsViewModel = mPostsViewModel
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun setupView() {
        mAdapter = PostsAdapter()
        setupAdapter()
        handleUsersCall()
    }

    override fun setupUiListener() {
        mBinding.swipeRefreshLayoutPostsFragment.setOnRefreshListener {
            handleUsersCall()
        }
    }

    override fun setupObservers() {
        getUserPosts()
    }

    private fun handleUsersCall() {
        mBinding.swipeRefreshLayoutPostsFragment.isRefreshing = false
        if (mPostsViewModel.checkNetworkConnection())
            getUserPosts()
        else
            Toast.makeText(
                requireContext(),
                R.string.no_internet_connection,
                Toast.LENGTH_SHORT
            ).show()

    }

    private fun getUserPosts() {
        mJob?.cancel()
        mJob = lifecycleScope.launch {
            mPostsViewModel.getUserPosts(mArgs.userId).collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        mBinding.recyclerViewPostsFragment.adapter = mAdapter
    }

}