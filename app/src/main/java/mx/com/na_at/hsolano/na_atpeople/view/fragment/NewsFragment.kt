package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository
import mx.com.na_at.hsolano.na_atpeople.view.adapter.NewsAdapter
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModel
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModelFactory

class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = NewsRepository()
        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(repository)
        ).get(NewsViewModel::class.java)

        val rvNews = view.findViewById<RecyclerView>(R.id.recycler_view_news)

        newsViewModel.requestGetAllNews()

        newsViewModel.news.observe(viewLifecycleOwner, { news ->
            val adapter = NewsAdapter(news)
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.adapter = adapter
        })
    }

}