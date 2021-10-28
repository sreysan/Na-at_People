package mx.com.na_at.hsolano.na_atpeople.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.network.response.ActivityRecordResponse
import mx.com.na_at.hsolano.na_atpeople.view.contract.ActivityRecordsListener

class ActivityRecordsAdapter(
    var list: List<ActivityRecordResponse>,
    private val listener: ActivityRecordsListener
) :
    RecyclerView.Adapter<ActivityRecordsAdapter.ViewHolder>() {
    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.text_view_name_project_total)
        val tvModifyProject: TextView = view.findViewById(R.id.text_view_modify_project)
        val tvDeleteProject: TextView = view.findViewById(R.id.text_view_delete_project)
        val rvActivitiesByProject: RecyclerView =
            view.findViewById(R.id.recycle_view_activities_by_project)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view_activity_records_summary,
            parent,
            false
        )
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProject = list[position]
        holder.tvName.text = currentProject.project.name
        holder.tvModifyProject.setOnClickListener {
            listener.onClickActivityRecordsModify(currentProject.activities)
        }
        holder.tvDeleteProject.setOnClickListener {
            listener.onClickActivityRecordsDelete(currentProject.project.name, "")
        }
        holder.rvActivitiesByProject.layoutManager = LinearLayoutManager(context)
        holder.rvActivitiesByProject.adapter =
            ActivitiesByProjectAdapter(currentProject.activities)

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
