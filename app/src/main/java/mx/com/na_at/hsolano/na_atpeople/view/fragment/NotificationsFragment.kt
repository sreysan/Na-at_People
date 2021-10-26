package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.repository.NotificationsRepositoryImpl
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.NotificationsAdapter
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NotificationViewModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.NotificationsViewModel

class NotificationsFragment : Fragment() {

    private lateinit var rvNotifications: RecyclerView
    private lateinit var notificationViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).showDaysSinceLastRecordTab()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotifications = view.findViewById(R.id.recycle_view_notifications)
        rvNotifications.layoutManager = LinearLayoutManager(activity)

        context?.let {
            val repository = NotificationsRepositoryImpl(it)
            notificationViewModel = ViewModelProvider(
                this,
                NotificationViewModelFactory(repository)
            ).get(NotificationsViewModel::class.java)
        }
        notificationViewModel.getAllNotifications()
        notificationViewModel.listModel.observe(viewLifecycleOwner, { listNotifications ->
            rvNotifications.adapter = NotificationsAdapter(listNotifications)
        })
    }
}