package my.pet.project.task1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.pet.project.task1.R
import my.pet.project.task1.data.Contact

class ContactRecyclerViewAdapter(
    val onClickItem: (id: Long) -> Unit,
    val onClickEdit: (id: Long) -> Unit
) : RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder>() {

    private var dataSet: List<Contact> = listOf()

    fun setItemList(newItemsList: List<Contact>){
        dataSet = newItemsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_contact_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = dataSet[position].name
        holder.textViewInitials.text = dataSet[position].name.substring(0,1)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewInitials: TextView
        val imageViewEdit: ImageView

        init {
            textViewName = view.findViewById(R.id.tv_contact_item_name)
            textViewInitials = view.findViewById(R.id.tv_contact_item_initials)
            imageViewEdit = view.findViewById(R.id.iv_contact_item_edit)
            view.setOnClickListener {
                onClickItem(dataSet[adapterPosition].id)
            }
            imageViewEdit.setOnClickListener {
                onClickEdit(dataSet[adapterPosition].id)
            }
        }
    }
}