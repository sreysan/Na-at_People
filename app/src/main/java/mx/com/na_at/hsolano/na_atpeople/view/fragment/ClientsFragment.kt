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
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.util.Constants.CLIENT_ID
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.RegisterActivityAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.RegisterActivityEvents
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel

class ClientsFragment : Fragment(), RegisterActivityEvents {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvDate = view.findViewById<TextView>(R.id.text_view_date_selector)

        tvDate.text = (activity as HomeActivity).olderDay
        val repository = RegisterActivityRepository()

        val viewModel = ViewModelProvider(this, RegisterActivityModelFactory(repository)).get(
            RegisterActivityViewModel::class.java
        )

        val rvClients = view.findViewById<RecyclerView>(R.id.recycler_view_clients)
        rvClients.layoutManager = LinearLayoutManager(context)

        viewModel.getClients()
        viewModel.clients.observe(viewLifecycleOwner, {
            rvClients.adapter = RegisterActivityAdapter(it, this)
        })

    }

    override fun onItemClickListener(item: Pair<String, String>) {
        val activity = (activity as HomeActivity)
        activity.setTabClientName(item.second)
        activity.bundle.putString(CLIENT_ID, item.first)
        activity.navigateToFragment(R.id.projectsFragment)
    }

}