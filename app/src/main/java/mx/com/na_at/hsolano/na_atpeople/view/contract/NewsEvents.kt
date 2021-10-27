package mx.com.na_at.hsolano.na_atpeople.view.contract

import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.NewsDetail

interface NewsEvents {
    fun onNewsClickListener(postId: String)
}

interface GetNewsCallback {

    fun onGetNewsSuccessful(news: List<News>)

    fun onGetNewsFailure(error: Throwable)
}

interface GetNewsDetailCallback {
    fun onGetNewsDetailSuccessful(newsDetail: NewsDetail)
    fun onGetNewsDetailFailure(error: Throwable)
}

class GetNewsError : Throwable()

class GetNewsDetailError : Throwable()