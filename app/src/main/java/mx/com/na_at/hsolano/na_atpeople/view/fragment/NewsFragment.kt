package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = NewsRepository()
        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(repository)
        ).get(NewsViewModel::class.java)


        newsViewModel.requestGetAllNews()

        newsViewModel.news.observe(viewLifecycleOwner, { news ->
            for (currentNews in news) {
                println(currentNews)
            }
        })
    }

}