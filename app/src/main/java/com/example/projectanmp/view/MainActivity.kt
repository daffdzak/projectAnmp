package com.example.projectanmp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.projectanmp.R
import com.example.projectanmp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
        binding.bottomNav.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)

        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemGame -> navController.navigate(R.id.itemGame)
                R.id.itemSchedule -> navController.navigate(R.id.itemSchedule)
                R.id.itemProfile -> navController.navigate(R.id.itemProfile)
                R.id.itemTeam -> navController.navigate(R.id.itemTeam)
                R.id.itemAchievement -> navController.navigate(R.id.itemAchievement)
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawers()
            true
        } else {
            navController.navigateUp() || super.onSupportNavigateUp()
        }
    }
}
