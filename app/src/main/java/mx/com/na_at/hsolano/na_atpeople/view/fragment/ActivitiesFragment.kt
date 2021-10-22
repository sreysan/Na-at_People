package mx.com.na_at.hsolano.na_atpeople.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.ActivityHour
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.view.adapter.ActivitiesAdapter
import mx.com.na_at.hsolano.na_atpeople.view.contract.ActivitiesHoursEvents
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel

class ActivitiesFragment : Fragment(), ActivitiesHoursEvents {

    private lateinit var viewModel: RegisterActivityViewModel
    private lateinit var currentActivity: ActivityHour

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerViewActivities = view.findViewById<RecyclerView>(R.id.recycler_view_activities)
        recyclerViewActivities.layoutManager = LinearLayoutManager(context)

        val repository = RegisterActivityRepository()

        viewModel = ViewModelProvider(this, RegisterActivityModelFactory(repository)).get(
            RegisterActivityViewModel::class.java
        )

        viewModel.requestGetAllActivities()
        viewModel.activities.observe(viewLifecycleOwner, {
            recyclerViewActivities.adapter =
                ActivitiesAdapter(it, viewModel, this, viewLifecycleOwner)
        })

        viewModel.lessButtonState.observe(viewLifecycleOwner, {
            println("Button - is enabled" + it.toString())
        })
    }

    override fun onAddHourClicked(activityHour: ActivityHour) {
        currentActivity = activityHour
        viewModel.addHours()
    }

    override fun onLessHourClicked(activityHour: ActivityHour) {
        TODO("Not yet implemented")
    }
}