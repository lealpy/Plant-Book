package com.example.plantsbook.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.plantsbook.R
import com.example.plantsbook.data.models.PlantType
import com.example.plantsbook.databinding.ActivityStartBinding
import com.example.plantsbook.presentation.garden.GardenFragment
import com.example.plantsbook.presentation.plant_info.PlantInfoFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding
    lateinit var toggle: ActionBarDrawerToggle
    private val mainViewModel: MainViewModel by viewModels() // 'androidx.fragment:fragment-ktx:1.3.6'

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
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
        mainViewModel.navigationPath.observe(this, { navView ->
            when (navView) {
                is MainViewModel.NavPath.GardenNavPath -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_frame_layout, GardenFragment.newInstance()).commit()
                }
                is MainViewModel.NavPath.PlantInfoNavPath -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.activity_frame_layout,
                            PlantInfoFragment.newInstance(navView.plantType ?: PlantType.zhirianka)
                        )
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
                mainViewModel.onNavDrawerClicked()
            }
            R.id.nav_zhirianka -> {
                mainViewModel.onNavDrawerClicked(PlantType.zhirianka)
            }
            R.id.nav_muholovka -> {
                mainViewModel.onNavDrawerClicked(PlantType.muholovka)
            }
            R.id.nav_nepentes -> {
                mainViewModel.onNavDrawerClicked(PlantType.nepentes)
            }
            R.id.nav_rosianka -> {
                mainViewModel.onNavDrawerClicked(PlantType.rosianka)
            }
            R.id.nav_sarracenia -> {
                mainViewModel.onNavDrawerClicked(PlantType.sarracenia)
            }
            R.id.nav_puzirchatka -> {
                mainViewModel.onNavDrawerClicked(PlantType.puzirchatka)
            }
            R.id.nav_darlingtonia -> {
                mainViewModel.onNavDrawerClicked(PlantType.darlingtonia)
            }
        }
    }

}
