package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.NewsAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.NewsEvents
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModel
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModelFactory

class NewsFragment : Fragment(), NewsEvents {

    private lateinit var newsViewModel: NewsViewModel
    private val POST_ID = "POST_ID"

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
            val adapter = NewsAdapter(news, this)
            rvNews.layoutManager = LinearLayoutManager(context)
            rvNews.adapter = adapter
        })
    }

    override fun onNewsClickListener(postId: String) {
        //action_newsFragment_to_newsDetail
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.fragment_navigation_host) as NavHostFragment
        val navController = navHostFragment.navController
        val bundle = Bundle()
        bundle.putString(POST_ID, postId)
        navController.navigate(R.id.action_newsFragment_to_newsDetailFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).onExpandToolbar()
    }
}