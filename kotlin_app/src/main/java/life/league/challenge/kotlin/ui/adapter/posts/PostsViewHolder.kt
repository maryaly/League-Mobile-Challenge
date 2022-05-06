package life.league.challenge.kotlin.ui.adapter.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.databinding.ItemPostBinding
import life.league.challenge.kotlin.model.Post


class PostsViewHolder(
    private val mContext: Context,
    private val mBinding: ItemPostBinding,
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(currentItem: Post) {
        mBinding.textViewItemPostBody.text = currentItem.body
        mBinding.textViewItemPostTitle.text = currentItem.title
    }


    companion object {
        fun create(
            parent: ViewGroup,
        ): PostsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_post, parent, false)
            val binding = ItemPostBinding.bind(view)
            return PostsViewHolder(
                parent.context,
                binding
            )
        }
    }
}