package mx.com.na_at.hsolano.na_atpeople.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.com.na_at.hsolano.na_atpeople.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navigationController = findNavController(R.id.fragment_navigation_host)

        navigationView.setupWithNavController(navigationController)

        val footerLayout = findViewById<ConstraintLayout>(R.id.constraint_layout_footer)

    }
}