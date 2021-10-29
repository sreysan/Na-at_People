package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository
import mx.com.na_at.hsolano.na_atpeople.util.Constants.POST_ID
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.NewsAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.NewsEvents
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModel
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NewsViewModelFactory

class NewsFragment : Fragment(), NewsEvents {

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var etSearchView: EditText

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

        context?.let {
            val repository = NewsRepository(it)
            newsViewModel = ViewModelProvider(
                this,
                NewsViewModelFactory(repository)
            ).get(NewsViewModel::class.java)
        }

        val rvNews = view.findViewById<RecyclerView>(R.id.recycler_view_news)
        rvNews.layoutManager = LinearLayoutManager(context)
        val news = mutableListOf<News>()

        adapter = NewsAdapter(news, this)
        rvNews.adapter = adapter


        newsViewModel.news.observe(viewLifecycleOwner, { allNews ->
            news.addAll(allNews.toMutableList())
            adapter.notifyDataSetChanged()
        })

        etSearchView = view.findViewById(R.id.edit_text_search_view)
        etSearchView.imeOptions = EditorInfo.IME_ACTION_DONE
        var querySearch = ""
        etSearchView.doAfterTextChanged {
            querySearch = it.toString()
            adapter.filter(querySearch)
        }

        etSearchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                etSearchView.clearFocus()

                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            true
        }
    }

    override fun onResume() {
        (activity as HomeActivity).showDaysSinceLastRecordTab()
        newsViewModel.requestGetAllNews()
        etSearchView.text.clear()
        super.onResume()
    }


    override fun onNewsClickListener(postId: String) {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.fragment_navigation_host) as NavHostFragment
        val navController = navHostFragment.navController
        val bundle = Bundle()
        bundle.putString(POST_ID, postId)
        navController.navigate(R.id.action_newsFragment_to_newsDetailFragment, bundle)
    }

}