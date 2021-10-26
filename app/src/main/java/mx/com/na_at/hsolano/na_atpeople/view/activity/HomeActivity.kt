package mx.com.na_at.hsolano.na_atpeople.view.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var footerLayout: ConstraintLayout
    lateinit var clientTabLayout: ConstraintLayout
    lateinit var dateTabLayout: LinearLayout
    lateinit var navigationController: NavController
    lateinit var navigationView: BottomNavigationView

    private val calendars = mutableListOf<Calendar>()
    lateinit var olderDay: String
    var bundle = Bundle()

    // TODO: REPLACE WITH SERVICE VALUE
    private val daysPending = 3
    private lateinit var tvTabDate: TextView
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

        navigationView.setupWithNavController(navigationController)
        footerLayout = findViewById(R.id.constraint_layout_footer)
        clientTabLayout = findViewById(R.id.constraint_layout_tab_client)
        dateTabLayout = findViewById(R.id.linear_layout_tab_date)
        tvTabDate = findViewById(R.id.text_view_tab_date)
        tvLabelFirstTab = findViewById(R.id.text_view_label_client)
        tvClientName = findViewById(R.id.text_view_client_name)
        val tvChangeClients = findViewById<TextView>(R.id.text_view_modify_client)
        tvChangeClients.setOnClickListener {
            backToClientsView()
        }

        handlePendingDates()
    }

    fun onCollapseToolbar() {
        footerLayout.visibility = View.GONE
    }

    private fun showClientsFragment() {
        if (navigationController.currentDestination?.id != R.id.clientsFragment) {
            navigationView.selectedItemId = R.id.registerActivityFragment
            navigationController.navigate(R.id.clientsFragment)
        }
    }

    // TODO MOVE TO VIEWMODEL
    private fun handlePendingDates() {
        val currentDate = Date()
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = currentDate

        for (index in daysPending downTo 1) {
            val dayBefore = currentCalendar[Calendar.DAY_OF_MONTH] - index
            val calendarBefore = Calendar.getInstance()
            calendarBefore[Calendar.DAY_OF_MONTH] = dayBefore
            calendars.add(calendarBefore)
        }
        calendars.add(currentCalendar)
        olderDay = DateUtils.getDateFormatted(calendars.first())
    }

    fun showTabRegisterDate() {
        tvTabDate.text = DateUtils.getDateFormatted(calendars.first())
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