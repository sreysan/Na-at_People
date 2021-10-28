package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.ActivityHour
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.util.Constants
import mx.com.na_at.hsolano.na_atpeople.util.Constants.PROJECT_ID
import mx.com.na_at.hsolano.na_atpeople.util.Constants.PROJECT_NAME
import mx.com.na_at.hsolano.na_atpeople.view.activity.HomeActivity
import mx.com.na_at.hsolano.na_atpeople.view.adapter.ActivitiesAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.ActivitiesHoursEvents
import mx.com.na_at.hsolano.na_atpeople.view.contract.NetworkCallback
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel

class ActivitiesFragment : Fragment(), ActivitiesHoursEvents {

    private lateinit var viewModel: RegisterActivityViewModel
    private var position = 0
    lateinit var projectId: String
    lateinit var projectName: String
    lateinit var buttonContinue: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        projectId = arguments?.getString(PROJECT_ID) ?: ""
        projectName = arguments?.getString(PROJECT_NAME) ?: ""
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onResume() {
        (activity as HomeActivity).setTabInfo(getString(R.string.label_project), projectName)
        (activity as HomeActivity).showTabRegisterDate()
        super.onResume()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewActivities = view.findViewById<RecyclerView>(R.id.recycler_view_activities)
        recyclerViewActivities.layoutManager = LinearLayoutManager(context)
        buttonContinue = view.findViewById(R.id.button_continue)
        context?.let {
            val repository = RegisterActivityRepository(it)

            viewModel = ViewModelProvider(this, RegisterActivityModelFactory(repository)).get(
                RegisterActivityViewModel::class.java
            )
        }

        val activities = mutableListOf<ActivityHour>()
        val adapter = ActivitiesAdapter(activities, this)
        recyclerViewActivities.adapter = adapter

        viewModel.requestGetAllActivities()

        viewModel.isConnected.observe(viewLifecycleOwner, {
            (activity as HomeActivity).showOrHideInternetMessage(it, object : NetworkCallback {
                override fun tryAgainRequest() {
                    viewModel.requestGetAllActivities()
                }
            })
        })

        viewModel.activities.observe(viewLifecycleOwner, {
            activities.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.activity.observe(viewLifecycleOwner, {
            activities[position] = it
            adapter.notifyItemChanged(position)
        })

        val tvHours = view.findViewById<TextView>(R.id.text_view_hours_total)
        viewModel.hoursLiveData.value = 0
        viewModel.hoursLiveData.observe(viewLifecycleOwner, {
            tvHours.text = getString(R.string.template_hours, it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) (activity as HomeActivity).showLoader()
            else (activity as HomeActivity).hideLoader()
        })

        buttonContinue.setOnClickListener {
            (activity as HomeActivity).navigateToFragment(R.id.summaryActivityRecordsFragment)
        }
    }

    override fun onAddHourClicked(activityHour: ActivityHour, position: Int) {
        this.position = position
        viewModel.activity.value = activityHour
        viewModel.addHours()
    }

    override fun onLessHourClicked(activityHour: ActivityHour, position: Int) {
        this.position = position
        viewModel.activity.value = activityHour
        viewModel.lessHours()
    }
}