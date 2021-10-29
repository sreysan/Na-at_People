package mx.com.na_at.hsolano.na_atpeople.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.view.contract.NewsEvents
import com.google.android.gms.common.data.DataHolder


class NewsAdapter(var news: MutableList<News>, private val listener: NewsEvents) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val originalList = news
    private var lastSearchSize = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = news[position]
        holder.tvTitle.text = post.title
        holder.tvDate.text = post.publishDate
        holder.tvSummary.text = post.summary
        holder.tvShowDetail.setOnClickListener {
            listener.onNewsClickListener(post.id)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun updateList(list: MutableList<News>) {
        news = list
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        val temp: MutableList<News> = ArrayList()

        if (text.length < lastSearchSize) {
            news = originalList
        }
        for (d in news) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.title.lowercase().contains(text.lowercase())) {
                temp.add(d)
            }
        }
        //update recyclerview
        this.updateList(temp)
        lastSearchSize = text.length
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.text_view_title)
        val tvDate = view.findViewById<TextView>(R.id.text_view_date)
        val tvSummary = view.findViewById<TextView>(R.id.text_view_summary)
        val tvShowDetail = view.findViewById<TextView>(R.id.text_view_show)
    }
}