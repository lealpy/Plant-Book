package com.example.plantsbook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.DataModel
import com.example.myapplication.GardenFragment
import com.example.myapplication.PlantInfoFragment
import com.example.plantsbook.*
import com.example.plantsbook.databinding.ActivityStartBinding
import com.google.android.material.navigation.NavigationView

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    lateinit var toggle: ActionBarDrawerToggle
    private var launcher : ActivityResultLauncher<Intent>? = null
    private val dataModel : DataModel by viewModels() // для передачи данных между фрагментами. Не забыть добавить зависимость 'androidx.fragment:fragment-ktx:1.3.6'


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigation_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            onClickMenu(it.itemId)
            drawerLayout.closeDrawer(GravityCompat.START) // Закрываем меню после нажатия выбора любого пункта меню
            true
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result : ActivityResult ->
            if(result.resultCode == RESULT_OK) {
                //Передача данных
            }
        }



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
               //launcher?.launch(Intent(this, MainActivity::class.java))
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, GardenFragment.newInstance()).commit()

            }
            R.id.nav_zhirianka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.messageForPlantInfoFragment.value = zhirianka
           //     binding.tittleFlowerPage.text = getString(R.string.zhirianka)
           //     binding.imageFlowerPage.setImageResource(R.drawable.zhirianka0)
           //     binding.descriptionFlowerPage.text = getString(R.string.zhirianka_description)
            }
            R.id.nav_muholovka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.messageForPlantInfoFragment.value = muholovka
            //    binding.tittleFlowerPage.text = getString(R.string.muholovka)
            //    binding.imageFlowerPage.setImageResource(R.drawable.muholovka1)
            //    binding.descriptionFlowerPage.text = getString(R.string.muholovka_description)
            }
            R.id.nav_nepentes -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.messageForPlantInfoFragment.value = nepentes
            //    binding.tittleFlowerPage.text = getString(R.string.nepentes)
            //    binding.imageFlowerPage.setImageResource(R.drawable.nepentes2)
            //    binding.descriptionFlowerPage.text = getString(R.string.nepentes_description)
            }
            R.id.nav_rosianka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.messageForPlantInfoFragment.value = rosianka
            //    binding.tittleFlowerPage.text = getString(R.string.rosianka)
            //    binding.imageFlowerPage.setImageResource(R.drawable.rosyanka3)
            //    binding.descriptionFlowerPage.text = getString(R.string.rosianka_description)
            }
            R.id.nav_sarracenia -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.messageForPlantInfoFragment.value = sarracenia
            //    binding.tittleFlowerPage.text = getString(R.string.sarracenia)
            //    binding.imageFlowerPage.setImageResource(R.drawable.sarracenia4)
            //    binding.descriptionFlowerPage.text = getString(R.string.sarracenia_description)
            }
            R.id.nav_puzirchatka -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.messageForPlantInfoFragment.value = puzirchatka
            //    binding.tittleFlowerPage.text = getString(R.string.puzirchatka)
            //    binding.imageFlowerPage.setImageResource(R.drawable.puzirchatka5)
            //    binding.descriptionFlowerPage.text = getString(R.string.puzirchatka_description)
            }
            R.id.nav_darlingtonia -> {
                supportFragmentManager.beginTransaction().replace(R.id.activity_frame_layout, PlantInfoFragment.newInstance()).commit()
                dataModel.messageForPlantInfoFragment.value = darlingtonia
            //    binding.tittleFlowerPage.text = getString(R.string.darlingtonia)
            //    binding.imageFlowerPage.setImageResource(R.drawable.darlingtonia6)
            //    binding.descriptionFlowerPage.text = getString(R.string.darlingtonia_description)
            }
        }
    }
}
