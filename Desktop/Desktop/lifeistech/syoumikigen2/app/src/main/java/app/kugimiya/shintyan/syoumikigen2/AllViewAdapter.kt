package app.kugimiya.shintyan.syoumikigen2

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_all_data_cell.view.*

class AllViewAdapter(
    private val context: Context,
    private var taskList: OrderedRealmCollection<AllViewData>?,
    private var listener: OnItemClickListener,
    private val autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<AllViewData, AllViewAdapter.AllViewHolder>(taskList, autoUpdate) {



    override fun getItemCount(): Int = taskList?.size ?: 0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        val task: AllViewData = taskList?.get(position) ?: return

        holder.container.setOnClickListener{
            listener.onItemClick(task)
        }

        //holder.imageView.setImageResource(task.imageId)
        holder.contentTextView.text = task.name
        holder.kigen.text =task.kigen.toString().substring(0,4)+"/"+task.kigen.toString().substring(4,6)+"/"+task.kigen.toString().substring(6,8)
        holder.place.text =task.place

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):AllViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_all_data_cell, viewGroup, false)
        return AllViewHolder(v)
    }

    class AllViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container : LinearLayout = view.container
        //val imageView: ImageView = view.imageView
        val contentTextView: TextView = view.contentTextView
        val kigen: TextView = view.dateTextView
        val place: TextView = view.placeTextView
    }
    interface OnItemClickListener {
        fun onItemClick(item: AllViewData)
    }

}