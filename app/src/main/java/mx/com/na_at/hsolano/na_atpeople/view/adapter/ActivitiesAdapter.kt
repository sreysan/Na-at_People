package mx.com.na_at.hsolano.na_atpeople.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.ActivityHour
import mx.com.na_at.hsolano.na_atpeople.view.contract.ActivitiesHoursEvents
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel

class ActivitiesAdapter(
    private val values: List<ActivityHour>,
    private val viewModel: RegisterActivityViewModel,
    private val listener: ActivitiesHoursEvents,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view_activities_item,
            parent, false
        )
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentValue = values[position]

        holder.tvActivityTitle.text = currentValue.name

        holder.tvHours.text = currentValue.isLessEnable.toString()

        holder.ivLess.setOnClickListener {
            // listener.onLessHourClicked(currentValue)
            viewModel.lessHour()
        }
        holder.ivAdd.setOnClickListener {
            viewModel.addHours()
            // listener.onAddHourClicked(currentValue)
        }

        viewModel.count.observe(viewLifecycleOwner, {
            holder.tvHours.text = "$it hrs"
        })

        viewModel.lessButtonState.observe(viewLifecycleOwner, {
            if (it)
                holder.ivLess.setColorFilter(ContextCompat.getColor(context, R.color.primary))
            else
                holder.ivLess.setColorFilter(ContextCompat.getColor(context, R.color.gray_9B9B9B))

        })

        viewModel.addButtonState.observe(viewLifecycleOwner, {
            if (it)
                holder.ivAdd.setColorFilter(ContextCompat.getColor(context, R.color.primary))
            else
                holder.ivAdd.setColorFilter(ContextCompat.getColor(context, R.color.gray_9B9B9B))

        })

    }

    override fun getItemCount(): Int {
        return values.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvActivityTitle = view.findViewById<TextView>(R.id.text_view_activity_title)
        val tvHours = view.findViewById<TextView>(R.id.text_view_hours)
        val ivAdd = view.findViewById<ImageView>(R.id.image_view_add)
        val ivLess = view.findViewById<ImageView>(R.id.image_view_less)
    }
}