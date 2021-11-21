package ge.nlatsabidze.passcode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.nlatsabidze.passcode.databinding.ImageLayoutBinding
import ge.nlatsabidze.passcode.databinding.ItemLayoutBinding

class RecyclerItemAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val firstItemView = 1
        const val secondItemView = 2
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
        fun onLastCharacterDeleted(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    var numberList: MutableList<Data> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemViewHolder(private val binding: ItemLayoutBinding, listener: onItemClickListener): RecyclerView.ViewHolder(binding.root) {
        private lateinit var data: Data

        fun onBind() {
            data = numberList[absoluteAdapterPosition]
            binding.numberButton.text = data.number.toString()
        }

        init {
            binding.numberButton.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

    inner class ImageViewHolder(private val binding: ImageLayoutBinding, listener: onItemClickListener): RecyclerView.ViewHolder(binding.root) {

        private lateinit var data: Data
        fun onBind() {
            data = numberList[absoluteAdapterPosition]
            binding.buttonTwo.setImageResource(data.number)
        }

        init {
            binding.buttonTwo.setOnClickListener {
                listener.onLastCharacterDeleted(absoluteAdapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder  {
        return if (viewType == firstItemView) {
            ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), mListener)
        } else {
            ImageViewHolder(ImageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), mListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == firstItemView) {
            (holder as ItemViewHolder).onBind()
        } else {
            (holder as ImageViewHolder).onBind()
        }
    }

    override fun getItemCount() = numberList.size

    override fun getItemViewType(position: Int): Int {
        if (numberList[position].type == "Image") {
            return 2
        }
        return 1
    }
}