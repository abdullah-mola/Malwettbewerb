package abdullah.mola.malwettbewerb.viewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.model.Submission
import abdullah.mola.malwettbewerb.viewholders.RCViewHolder
import com.squareup.picasso.Picasso

class ShowAllRCAdapter(val onItemClicked: (Submission) -> Unit) : RecyclerView.Adapter<RCViewHolder>() {
    private var showAllDataList: List<Submission> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RCViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.showall_cv, parent, false)
        return RCViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RCViewHolder, position: Int) {
        val currentItem = showAllDataList[position]
        holder.childAge.text = currentItem.childAge
        holder.childName.text = currentItem.childFirstname + ", "
        currentItem.image?.publicLocation?.let {
            Picasso.get()
                .load("https://sumsi.dev.webundsoehne.com${it}")
                .into(holder.imageShowAll)
        }
        holder.itemView.setOnClickListener {
            onItemClicked.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return showAllDataList.size
    }

    fun setListData(showAllDataList: List<Submission>) {
        this.showAllDataList = showAllDataList
    }

}