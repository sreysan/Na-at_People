package mx.com.na_at.hsolano.na_atpeople.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.network.response.Activity

class ActivitiesByProjectAdapter(private val list: List<Activity>) :
    RecyclerView.Adapter<ActivitiesByProjectAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.text_view_activity_name_by_project)
        val tvHours: TextView = view.findViewById(R.id.text_view_hours_by_project)
        val divider: View = view.findViewById(R.id.divider2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view_activity_by_project,
            parent,
            false
        )
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentActivity = list[position]
        if (position == (list.size - 1)) {
            holder.divider.visibility = View.GONE
        }
        holder.tvName.text = currentActivity.activity.name
        holder.tvHours.text = context.getString(R.string.template_hrs, currentActivity.duration)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}