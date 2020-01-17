package cat.helm.android

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cat.helm.common.showlist.domain.model.TvShow
import kotlinx.android.synthetic.main.item_tv_show.view.*
import kotlin.properties.Delegates

class TvShowAdapter constructor() :
    androidx.recyclerview.widget.RecyclerView.Adapter<TvShowViewHolder>() {

    var tvShows: List<TvShow> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { old, new ->
            old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {

        holder.bind(tvShows[position])
    }

    override fun getItemCount(): Int = tvShows.size

    fun autoNotify(oldList: List<TvShow>, newList: List<TvShow>, compare: (TvShow, TvShow) -> Boolean) {

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                return compare(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })

        diff.dispatchUpdatesTo(this)
    }
}

class TvShowViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    fun bind(tvShow: TvShow) {
        itemView.tv_show_name.text = tvShow.name
        itemView.image.bind(itemView.context.getString(R.string.baseImageUrl) + tvShow.image)
        itemView.description.text = tvShow.overview

    }
}
