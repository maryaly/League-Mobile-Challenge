package life.league.challenge.kotlin.ui.adapter.users

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.databinding.ItemUserBinding
import life.league.challenge.kotlin.model.User


class UsersViewHolder(
    private val mContext: Context,
    private val mBinding: ItemUserBinding,
    private val onItemClickListener: ((User) -> Unit),
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(currentItem: User) {
        mBinding.constraintLayoutItemUser.setOnClickListener {
            onItemClickListener(currentItem)
        }

        mBinding.textViewItemUserDescription.text =
            "address: ${currentItem.address?.city}, ${currentItem.address?.street}, ${currentItem.address?.suite}, ${currentItem.address?.zipcode} \n" +
                    "website: ${currentItem.website} \n" +
                    "company: ${currentItem.company?.name}, ${currentItem.company?.catchPhrase}"
        mBinding.textViewItemUserTitle.text = currentItem.name
        mBinding.textViewItemUserUsername.text = currentItem.username
        if (currentItem.avatar?.thumbnail !== null) {
            Glide.with(mContext)
                .load(currentItem.avatar.thumbnail)
                .into(mBinding.imageViewItemUserAvatarImage)
        } else {
            mBinding.imageViewItemUserAvatarImage.setImageResource(R.drawable.image_placeholder)

//            mBinding.imageViewItemUserAvatarImage.load(
//                "${currentItem.avatar?.thumbnail}"
//            ) {
//                memoryCachePolicy(CachePolicy.ENABLED)
//                crossfade(true)
//                error(R.drawable.image_placeholder)
//                transformations(CircleCropTransformation())
            }
        }


        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: ((User) -> Unit)
            ): UsersViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user, parent, false)
                val binding = ItemUserBinding.bind(view)
                return UsersViewHolder(
                    parent.context,
                    binding,
                    onItemClickListener
                )
            }
        }
    }