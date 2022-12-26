package abdullah.mola.malwettbewerb.fragments_a_activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.model.LoginRequestBody
import abdullah.mola.malwettbewerb.model.LoginResponse
import abdullah.mola.malwettbewerb.model.NetworkManager
import android.app.AlertDialog
import com.google.android.material.navigation.NavigationView

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var myToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        myToolbar=findViewById(R.id.myToolbar)
//        myToolbar.title="sumsi"
//
//        setSupportActionBar(myToolbar)

        reqestPermissions()
        //Networking
        NetworkManager.sumsiBackend.login(LoginRequestBody("admin@csaw.at", "pw4sumsiadmin"))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val body = response.body()!!

                    NetworkManager.authToken = body.token
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                }

            })
        //NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        navigationView.setupWithNavController(navController)
        //navigation drawer
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //    PERMISSIONS
    private fun hasWriteExternalStorgepermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun reqestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if (!hasWriteExternalStorgepermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!hasCameraPermission()) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && grantResults.isNotEmpty()) {

            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionRequest", "${permissions[i]} granted.")
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navFragmentContainer)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.datenschutz -> Navigation.findNavController(this, R.id.navFragmentContainer)
                .navigate(
                    R.id.datenschutz2
                )

            R.id.DisclaimerItem -> Navigation.findNavController(this, R.id.navFragmentContainer)
                .navigate(
                    R.id.disclaimer
                )

            R.id.teilnahmebedingungen -> Navigation.findNavController(
                this,
                R.id.navFragmentContainer
            ).navigate(R.id.teilnamebedingungen2)

            R.id.itemAGB -> Navigation.findNavController(
                this,
                R.id.navFragmentContainer
            ).navigate(R.id.AGB)

            R.id.impressum -> Navigation.findNavController(this, R.id.navFragmentContainer)
                .navigate(
                    R.id.impressum2
                )

            R.id.cockiesRechtLinien -> Navigation.findNavController(this, R.id.navFragmentContainer)
                .navigate(
                    R.id.cockieRichtlinien
                )

            R.id.sumsiInfoSite -> Navigation.findNavController(this, R.id.navFragmentContainer)
                .navigate(
                    R.id.allInfosFragment
                )

        }
        item.isChecked = true
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawers()

        return true
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.exit_dialog_title))
        builder.setMessage(getString(R.string.exit_dialog_message))
        builder.setPositiveButton(getString(R.string.option_yes)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            super.onBackPressed()
        }
        builder.setNegativeButton(getString(R.string.option_no)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.show()
    }
}