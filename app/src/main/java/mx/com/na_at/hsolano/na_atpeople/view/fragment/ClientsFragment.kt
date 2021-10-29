package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.ActivityRecords
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.util.Constants.CLIENT_ID
import mx.com.na_at.hsolano.na_atpeople.util.Constants.CLIENT_NAME
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.RegisterActivityAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.NetworkCallback
import mx.com.na_at.hsolano.na_atpeople.view.contract.RegisterActivityEvents
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel

class ClientsFragment : Fragment(), RegisterActivityEvents {

    lateinit var viewModel: RegisterActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_activity, container, false)
    }

    override fun onResume() {
        (activity as HomeActivity).hideInfoTabs()
        (activity as HomeActivity).hideDaysSinceLastRecordTab()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvDate = view.findViewById<TextView>(R.id.text_view_date_selector)

        tvDate.text = (activity as HomeActivity).tvTabDate.text

        context?.let {
            val repository =
                RegisterActivityRepository(it)

            viewModel = ViewModelProvider(this, RegisterActivityModelFactory(repository)).get(
                RegisterActivityViewModel::class.java
            )
        }

        val rvClients = view.findViewById<RecyclerView>(R.id.recycler_view_clients)
        rvClients.layoutManager = LinearLayoutManager(context)

        viewModel.isConnected.observe(viewLifecycleOwner, {
            (activity as HomeActivity).showOrHideInternetMessage(it, object : NetworkCallback{
                override fun tryAgainRequest() {
                    viewModel.requestGetAllClients()
                }
            })
        })

        viewModel.requestGetAllClients()


        viewModel.clients.observe(viewLifecycleOwner, {
            rvClients.adapter = RegisterActivityAdapter(it, this)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) (activity as HomeActivity).showLoader()
            else (activity as HomeActivity).hideLoader()
        })

    }

    override fun onItemClickListener(item: Pair<String, String>) {
        val activity = (activity as HomeActivity)
        activity.bundle.putString(CLIENT_ID, item.first)
        activity.bundle.putString(CLIENT_NAME, item.second)

        val currentItem = HomeActivity.registerEntity.activityRecords.size -1

        HomeActivity.registerEntity.activityRecords[currentItem].client.id = item.first
        HomeActivity.registerEntity.activityRecords[currentItem].client.name = item.second


        activity.navigateToFragment(R.id.action_clientsFragment_to_projectsFragment)
    }

}