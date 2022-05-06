package life.league.challenge.kotlin.ui.adapter.users

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import life.league.challenge.kotlin.model.User

class UsersAdapter (
    private val onItemClickListener: (User) -> Unit,
) :
    PagingDataAdapter<User, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            (holder as UsersViewHolder).bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UsersViewHolder.create(
            parent,
            onItemClickListener
        )
    }

    companion object {
        private val REPO_COMPARATOR =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(
                    oldItem: User,
                    newItem: User
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: User,
                    newItem: User
                ): Boolean =
                    oldItem == newItem
            }
    }
}