package id.rrdevfundamental.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.rrdevfundamental.data.db.entitiy.UserEntity
import id.rrdevfundamental.databinding.ItemUserBinding
import id.rrdevfundamental.utils.OnItemClicked
import id.rrdevfundamental.utils.loadImage

class FavoriteAdapter(private val onItemClicked: OnItemClicked) : RecyclerView.Adapter<FavoriteAdapter.EventHolder>() {

    private val list = mutableListOf<UserEntity>()

    fun addList(listData : List<UserEntity>){
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(
            EventDiffCallback(
                list,
                listData
            )
        )
        list.clear()
        list.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSwipedData(swipedPosition: Int): UserEntity = list[swipedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(onItemClicked, list[position])
    }

    override fun getItemCount(): Int = list.size

    class EventHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(onItemClicked: OnItemClicked, data: UserEntity) {
            with(binding){
                tvName.text = data.login
                tvCompany.text = data.type
                tvLocation.text = data.organizations_url
                ivUser.loadImage(data.avatar_url.toString())
            }
            binding.root.setOnClickListener {
                onItemClicked.onEventClick(data)
            }
        }
    }

    class EventDiffCallback(
        private var oldList : List<UserEntity>,
        private var newList : List<UserEntity>
    ) : DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return true
            return (oldList[oldItemPosition].id == newList[newItemPosition].id)
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}