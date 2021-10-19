package com.example.plantsbook.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.DataModel
import com.example.myapplication.GardenFragment
import com.example.myapplication.PlantInfoFragment
import com.example.plantsbook.*
import com.example.plantsbook.classes.PlantAdapter
import com.example.plantsbook.databinding.ActivityStartBinding
import com.google.android.material.navigation.NavigationView

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    lateinit var toggle: ActionBarDrawerToggle
    private val dataModel : DataModel by viewModels() // 'androidx.fragment:fragment-ktx:1.3.6'

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

        //При запуске приложения открываем GardenFragment
        supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, GardenFragment.newInstance()).commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickMenu(Id: Int) {
        when (Id) {
            R.id.nav_all_plants -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, GardenFragment.newInstance()).commit()
            }
            R.id.nav_zhirianka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.plantLiveData.value = zhirianka
            }
            R.id.nav_muholovka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.plantLiveData.value = muholovka
            }
            R.id.nav_nepentes -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.plantLiveData.value = nepentes
            }
            R.id.nav_rosianka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.plantLiveData.value = rosianka
            }
            R.id.nav_sarracenia -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.plantLiveData.value = sarracenia
            }
            R.id.nav_puzirchatka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.plantLiveData.value = puzirchatka
            }
            R.id.nav_darlingtonia -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.plantLiveData.value = darlingtonia
            }
        }
    }

}
