package mx.com.na_at.hsolano.na_atpeople.model.repository

import android.content.Context
import mx.com.na_at.hsolano.na_atpeople.util.ConnectivityUtil

open class HomeRepository(val context: Context) {

    fun isConnectedToInternet(): Boolean {
        return ConnectivityUtil.isOnline(context)
    }
}