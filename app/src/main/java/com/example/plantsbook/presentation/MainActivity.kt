package com.example.plantsbook.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.plantsbook.R
import com.example.plantsbook.databinding.ActivityMainBinding
import com.example.plantsbook.presentation.garden.GardenFragment
import com.example.plantsbook.presentation.library.LibraryFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    private val mainViewModel: MainViewModel by viewModels() // 'androidx.fragment:fragment-ktx:1.3.6'

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            onClickMenu(it.itemId)
            drawerLayout.closeDrawer(GravityCompat.START) // Закрываем меню после нажатия на любую кнопку меню
            true
        }

        //При запуске приложения сразу открываем фрагмент (по умолчанию в LiveData стоит GARDEN_FRAGMENT_NAME
        mainViewModel.navigationPath.observe(this, { navigationPath ->
            when (navigationPath) {
                is MainViewModel.NavPath.GardenNavPath -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_frame_layout, GardenFragment.newInstance())
                        .commit()
                }
                is MainViewModel.NavPath.LibraryNavPath -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.activity_frame_layout, LibraryFragment.newInstance())
                        .commit()
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickMenu(id: Int) {
        when (id) {
            R.id.nav_all_plants -> {
                mainViewModel.onNavDrawerClicked(MainViewModel.NavPath.GardenNavPath)
            }
            R.id.nav_library -> {
                mainViewModel.onNavDrawerClicked(MainViewModel.NavPath.LibraryNavPath)
            }
        }
    }

}
