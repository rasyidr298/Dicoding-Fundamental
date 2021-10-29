package id.rrdevfundamental.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.rrdevfundamental.data.model.User
import id.rrdevfundamental.databinding.ItemUserBinding
import id.rrdevfundamental.utils.OnItemClicked
import id.rrdevfundamental.utils.loadImage

// User Recycler View Adapter; Keyword : Adapter
class UserAdapter(private val onItemClicked: OnItemClicked) : RecyclerView.Adapter<UserAdapter.EventHolder>() {

    private val list = mutableListOf<User>()

    fun addList(listData : List<User>){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(onItemClicked, list[position])
    }

    override fun getItemCount(): Int = list.size

    class EventHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(onItemClicked: OnItemClicked, data: User) {
            with(binding){
                tvName.text = data.name
                tvCompany.text = data.company
                tvLocation.text = data.location
                ivUser.loadImage(data.avatar)
            }
            binding.root.setOnClickListener {
                onItemClicked.onEventClick(data)
            }
        }
    }

    class EventDiffCallback(
        private var oldList : List<User>,
        private var newList : List<User>
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