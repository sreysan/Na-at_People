package mx.com.na_at.hsolano.na_atpeople.view.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import mx.com.na_at.hsolano.na_atpeople.viewmodel.HomeViewModel
import mx.com.na_at.hsolano.na_atpeople.viewmodel.HomeViewModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityModelFactory
import mx.com.na_at.hsolano.na_atpeople.viewmodel.RegisterActivityViewModel
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var clientTabLayout: ConstraintLayout
    private lateinit var footerLayout: ConstraintLayout
    lateinit var dateTabLayout: LinearLayout
    lateinit var navigationController: NavController
    lateinit var navigationView: BottomNavigationView

    var bundle = Bundle()

    lateinit var tvTabDate: TextView
    lateinit var tvClientName: TextView
    lateinit var tvLabelFirstTab: TextView

    private lateinit var loaderContainer: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigationView = findViewById(R.id.bottomNavigationView)
        navigationController = findNavController(R.id.fragment_navigation_host)

        val tvRegisterHours = findViewById<TextView>(R.id.text_view_register_hours)
        loaderContainer = findViewById(R.id.linear_layout_loader)
        loaderContainer.background.alpha = 128

        tvRegisterHours.setOnClickListener {
            showClientsFragment()
        }
        footerLayout = findViewById(R.id.constraint_layout_footer)

        navigationView.setupWithNavController(navigationController)
        clientTabLayout = findViewById(R.id.constraint_layout_tab_client)
        dateTabLayout = findViewById(R.id.linear_layout_tab_date)
        tvTabDate = findViewById(R.id.text_view_tab_date)
        tvLabelFirstTab = findViewById(R.id.text_view_label_client)
        tvClientName = findViewById(R.id.text_view_client_name)
        val tvChangeClients = findViewById<TextView>(R.id.text_view_modify_client)
        tvChangeClients.setOnClickListener {
            backToClientsView()
        }
        val repository = RegisterActivityRepository()

        val viewModel = ViewModelProvider(this, HomeViewModelFactory(repository)).get(
            HomeViewModel::class.java
        )

        viewModel.requestGetActivityRecords()

        val tvDays = findViewById<TextView>(R.id.text_view_days)
        viewModel.days.observe(this, {
            val days = if (it == 1) "$it día" else "$it días"
            tvDays.text = getString(R.string.template_days, days)
        })

        viewModel.emptyDays.observe(this, {
            footerLayout.visibility = View.GONE
        })

        viewModel.olderDay.observe(this, {
            tvTabDate.text = it
        })
    }


    private fun showClientsFragment() {
        navigationView.selectedItemId = R.id.registerActivityFragment
        navigationController.navigate(R.id.clientsFragment)
    }

    fun showDaysSinceLastRecordTab() {
        footerLayout.visibility = View.VISIBLE
    }

    fun hideDaysSinceLastRecordTab() {
        footerLayout.visibility = View.GONE
    }

    fun showTabRegisterDate() {
        dateTabLayout.visibility = View.VISIBLE
        clientTabLayout.visibility = View.VISIBLE
    }

    fun hideInfoTabs() {
        dateTabLayout.visibility = View.GONE
        clientTabLayout.visibility = View.GONE
    }

    fun setTabInfo(title: String, content: String) {
        tvLabelFirstTab.text = title
        tvClientName.text = content
    }

    private fun backToClientsView() {
        navigationController.popBackStack()
        dateTabLayout.visibility = View.GONE
        clientTabLayout.visibility = View.GONE
    }

    fun navigateToFragment(fragmentId: Int) {
        navigationController.navigate(fragmentId, bundle)
    }

    fun showLoader() {
        loaderContainer.visibility = View.VISIBLE
    }

    fun hideLoader() {
        loaderContainer.visibility = View.GONE
    }
}