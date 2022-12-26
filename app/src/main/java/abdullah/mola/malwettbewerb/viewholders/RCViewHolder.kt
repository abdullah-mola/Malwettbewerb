package abdullah.mola.malwettbewerb.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import abdullah.mola.malwettbewerb.R

class RCViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    val imageShowAll: ImageView = itemView.findViewById(R.id.imgShowAll)
    val childName : TextView = itemView.findViewById(R.id.txtKidName)
    val childAge : TextView = itemView.findViewById(R.id.txtKidAge)
}