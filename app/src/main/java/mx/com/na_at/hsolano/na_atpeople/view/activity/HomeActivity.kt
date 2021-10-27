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
import mx.com.na_at.hsolano.na_atpeople.model.database.entity.Notification
import mx.com.na_at.hsolano.na_atpeople.model.repository.NotificationsRepositoryImpl
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.util.ConnectivityUtil
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import mx.com.na_at.hsolano.na_atpeople.view.contract.NetworkCallback
import mx.com.na_at.hsolano.na_atpeople.viewmodel.*
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var clientTabLayout: ConstraintLayout
    private lateinit var footerLayout: ConstraintLayout
    private lateinit var layoutErrors: ConstraintLayout
    lateinit var dateTabLayout: LinearLayout
    lateinit var navigationController: NavController
    lateinit var navigationView: BottomNavigationView

    var bundle = Bundle()

    lateinit var tvTabDate: TextView
    lateinit var tvClientName: TextView
    lateinit var tvLabelFirstTab: TextView
    lateinit var tvTryAgain: TextView

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
        layoutErrors = findViewById(R.id.layout_errors)

        navigationView.setupWithNavController(navigationController)
        clientTabLayout = findViewById(R.id.constraint_layout_tab_client)
        dateTabLayout = findViewById(R.id.linear_layout_tab_date)
        tvTabDate = findViewById(R.id.text_view_tab_date)
        tvLabelFirstTab = findViewById(R.id.text_view_label_client)
        tvClientName = findViewById(R.id.text_view_client_name)
        val tvChangeClients = findViewById<TextView>(R.id.text_view_modify_client)
        tvTryAgain = findViewById(R.id.text_view_try_again)

        tvChangeClients.setOnClickListener {
            backToClientsView()
        }
        val repository = RegisterActivityRepository(this)

        val viewModel = ViewModelProvider(this, HomeViewModelFactory(repository)).get(
            HomeViewModel::class.java
        )

        viewModel.isLoading.observe(this, {
            if (it) showLoader()
            else hideLoader()
        })

        viewModel.requestGetActivityRecords()

        viewModel.isConnected.observe(this, {
            showOrHideInternetMessage(it, object : NetworkCallback {
                override fun tryAgainRequest() {
                    viewModel.requestGetActivityRecords()
                    navigationController.popBackStack()
                    navigationController.navigate(R.id.newsFragment)
                }
            })
        })


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

    fun showOrHideInternetMessage(isOnline: Boolean, callback: NetworkCallback) {
        layoutErrors.visibility = if (isOnline) View.GONE else View.VISIBLE
        tvTryAgain.setOnClickListener {
            callback.tryAgainRequest()
        }
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

    private fun populateDB() {
        val repository = NotificationsRepositoryImpl(applicationContext)
        val notificationViewModel = ViewModelProvider(
            this,
            NotificationViewModelFactory(repository)
        ).get(NotificationsViewModel::class.java)
        val n1 = Notification(
            typeNotification = 1,
            titleNotification = "Actividad 1",
            dateNotification = "20/07/21"
        )
        val n2 = Notification(
            typeNotification = 2,
            titleNotification = "NAAT Fabrica de talentos",
            dateNotification = "18/07/21"
        )
        val n3 = Notification(
            typeNotification = 2,
            titleNotification = "NAAT Aniversario",
            dateNotification = "15/07/21"
        )
        val n4 = Notification(
            typeNotification = 1,
            titleNotification = "Actividad 9",
            dateNotification = "10/07/21"
        )
        notificationViewModel.insertNotification(n1)
        notificationViewModel.insertNotification(n2)
        notificationViewModel.insertNotification(n3)
        notificationViewModel.insertNotification(n4)
    }
}