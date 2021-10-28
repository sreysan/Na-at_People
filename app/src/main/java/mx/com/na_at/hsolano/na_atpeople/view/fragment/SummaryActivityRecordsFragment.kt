package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.network.response.Activity
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.ActivityRecordsAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.ActivityRecordsListener
import mx.com.na_at.hsolano.na_atpeople.view.contract.NetworkCallback
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel

class SummaryActivityRecordsFragment : Fragment(), ActivityRecordsListener {

    private lateinit var rvTotalProjects: RecyclerView
    private lateinit var tvTotalHours: TextView
    private lateinit var tvAddProject: TextView
    private lateinit var llFinish: LinearLayout
    private lateinit var viewModel: RegisterActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity_records_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTotalHours = view.findViewById(R.id.text_view_hours_worked)
        tvAddProject = view.findViewById(R.id.text_view_add_more_projects)
        llFinish = view.findViewById(R.id.layout_finish)

        rvTotalProjects = view.findViewById(R.id.recycle_view_total_projects)
        rvTotalProjects.layoutManager = LinearLayoutManager(activity)

        context?.let {
            val repository = RegisterActivityRepository(it)

            viewModel = ViewModelProvider(
                this,
                RegisterActivityModelFactory(repository)
            ).get(RegisterActivityViewModel::class.java)
        }

        viewModel.requestGetActivityRecords()

        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) (activity as HomeActivity).showLoader()
            else (activity as HomeActivity).hideLoader()
        })

        viewModel.isConnected.observe(viewLifecycleOwner, {
            (activity as HomeActivity).showOrHideInternetMessage(it, object : NetworkCallback {
                override fun tryAgainRequest() {
                    viewModel.requestGetActivityRecords()
                }
            })
        })


        viewModel.activityRecords.observe(viewLifecycleOwner, { listProjects ->
            rvTotalProjects.adapter = ActivityRecordsAdapter(listProjects, this)
        })


        (activity as HomeActivity).navigationController.popBackStack()
        (activity as HomeActivity).navigationController.popBackStack()
        (activity as HomeActivity).navigationController.popBackStack()

    }

    override fun onClickActivityRecordsDelete(nameProjectRecord: String, idActivityRecord: String) {
    }

    override fun onClickActivityRecordsModify(activities: List<Activity>) {
        (activity as HomeActivity).navigationController.popBackStack()
        (activity as HomeActivity).navigateToFragment(R.id.activitiesFragment)
    }
}