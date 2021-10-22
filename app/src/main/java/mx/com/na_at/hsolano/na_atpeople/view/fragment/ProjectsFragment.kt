package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.util.Constants.CLIENT_ID
import mx.com.na_at.hsolano.na_atpeople.util.Constants.PROJECT_ID
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.RegisterActivityAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.RegisterActivityEvents
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel


class ProjectsFragment : Fragment(), RegisterActivityEvents {

    lateinit var idClient: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idClient = arguments?.getString(CLIENT_ID) ?: ""
        return inflater.inflate(R.layout.fragment_register_activity, container, false)
    }

    override fun onResume() {
        (activity as HomeActivity).showTabRegisterDate()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSearch = view.findViewById<ImageView>(R.id.image_view_search)
        val tvDate = view.findViewById<TextView>(R.id.text_view_date_selector)
        btnSearch.visibility = View.GONE
        tvDate.visibility = View.GONE

        //TODO: RENAME IDs
        val rvProjects = view.findViewById<RecyclerView>(R.id.recycler_view_clients)
        rvProjects.layoutManager = LinearLayoutManager(context)

        val repository = RegisterActivityRepository()

        val viewModel = ViewModelProvider(this, RegisterActivityModelFactory(repository)).get(
            RegisterActivityViewModel::class.java
        )

        viewModel.getProjectsByClientId(idClient)
        viewModel.projects.observe(viewLifecycleOwner, {
            rvProjects.adapter = RegisterActivityAdapter(it, this)
        })

    }

    override fun onItemClickListener(item: Pair<String, String>) {
        val activity = (activity as HomeActivity)
        activity.setTabClientName(item.second)
        activity.bundle.putString(PROJECT_ID, item.first)
        //activity.navigateToFragment(R.id.projectsFragment)
    }
}
