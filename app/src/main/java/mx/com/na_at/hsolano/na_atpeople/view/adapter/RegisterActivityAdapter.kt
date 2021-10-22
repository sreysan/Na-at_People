package mx.com.na_at.hsolano.na_atpeople.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.view.contract.RegisterActivityEvents

class RegisterActivityAdapter(
    private val values: List<Pair<String, String>>,
    private val listener: RegisterActivityEvents
) :
    RecyclerView.Adapter<RegisterActivityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view_register_activity_item,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentValue = values[position]
        holder.tvName.text = currentValue.second
        holder.cvContainer.setOnClickListener {
            listener.onItemClickListener(currentValue)
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.text_view_name)
        val cvContainer = view.findViewById<CardView>(R.id.card_view_container)
    }
}