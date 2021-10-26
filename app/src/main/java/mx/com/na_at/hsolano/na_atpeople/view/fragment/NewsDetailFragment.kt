package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository
import mx.com.na_at.hsolano.na_atpeople.util.Constants.POST_ID
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModel
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModelFactory

class NewsDetailFragment : Fragment() {
    lateinit var newsViewModel: NewsViewModel

    private lateinit var postId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getString(POST_ID, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvTitle = view.findViewById<TextView>(R.id.text_view_news_detail_title)
        val tvDate = view.findViewById<TextView>(R.id.text_view_news_detail_date)
        val tvContent = view.findViewById<TextView>(R.id.text_view_news_detail_content)


        val repository = NewsRepository()
        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(repository)
        ).get(NewsViewModel::class.java)

        newsViewModel.requestGetNewsDetail(postId)

        newsViewModel.newDetail.observe(viewLifecycleOwner, { newsDetail ->
            tvTitle.text = newsDetail.title
            tvDate.text = newsDetail.publishDate
            tvContent.text = newsDetail.content
        })


    }

}