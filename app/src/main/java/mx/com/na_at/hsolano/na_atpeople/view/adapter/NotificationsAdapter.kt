package mx.com.na_at.hsolano.na_atpeople.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.database.entity.Notification

class NotificationsAdapter(private val list: List<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.text_view_title_notification)
        val tvType: TextView = view.findViewById(R.id.text_view_name_notification)
        val tvDate: TextView = view.findViewById(R.id.text_view_date_notification)
        val divColor: View = view.findViewById(R.id.divider_state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view_notification,
            parent, false
        )
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNotification = list[position]
        if (currentNotification.typeNotification == 1) {
            holder.divColor.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.green_28B19B
                )
            )
            holder.tvType.text = context.getString(R.string.label_activity_added)
            holder.tvTitle.text = context.getString(
                R.string.template_activity_added,
                currentNotification.titleNotification
            )
        } else {
            holder.divColor.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.gradient_start
                )
            )
            holder.tvType.text = context.getString(R.string.label_news)
            holder.tvTitle.text = currentNotification.titleNotification
        }
        holder.tvDate.text = currentNotification.dateNotification
    }

    override fun getItemCount(): Int {
        return list.size
    }
}