package life.league.challenge.kotlin.ui.adapter.posts

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import life.league.challenge.kotlin.model.Post

class PostsAdapter () :
    PagingDataAdapter<Post, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            (holder as PostsViewHolder).bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostsViewHolder.create(
            parent
        )
    }

    companion object {
        private val REPO_COMPARATOR =
            object : DiffUtil.ItemCallback<Post>() {
                override fun areItemsTheSame(
                    oldItem: Post,
                    newItem: Post
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Post,
                    newItem: Post
                ): Boolean =
                    oldItem == newItem
            }
    }
}