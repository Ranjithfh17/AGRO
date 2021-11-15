package `in`.global.agro.ui.activity

import `in`.global.agro.R
import `in`.global.agro.callbacks.CustomerPopupCallback
import `in`.global.agro.callbacks.MenuClickListener
import `in`.global.agro.data.model.Menu
import `in`.global.agro.data.model.SubMenu
import `in`.global.agro.databinding.ActivityMainBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.services.NotificationScheduler
import `in`.global.agro.ui.adapter.MenuAdapter
import `in`.global.agro.ui.adapter.SubMenuAdapter
import `in`.global.agro.ui.popup.CustomerCarePopup
import `in`.global.agro.utils.Contact
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : BaseActivity(), MenuClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var menuAdapter: MenuAdapter
    private var xOff = 0F
    private var yOff = 0F
    private lateinit var contactPermissionLauncher: ActivityResultLauncher<Array<String>>
    private var hasContactPermission = false
    private var hasReadPermission = false
    private val phoneNo = "+918940400792"


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController



        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.sales){
                binding.appBar.visibility=View.GONE
            }else{
                binding.appBar.visibility=View.VISIBLE
            }
        }




        setUpDrawerLayout()
        setUpRecyclerView()
        setMenuItems()

        contactPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                hasContactPermission =
                    it[Manifest.permission.WRITE_CONTACTS] ?: hasContactPermission
                hasReadPermission = it[Manifest.permission.READ_CONTACTS] ?: hasReadPermission

                if (hasContactPermission && hasReadPermission) {
                    addNewContact()
                } else {
                    applicationContext.showToast("Enable permissions ")
                }
            }


        val periodicWorkRequest = PeriodicWorkRequest.Builder(NotificationScheduler::class.java, 15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "notification",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )


        SubMenuAdapter.setOnSubMenuListener(object : SubMenuAdapter.Companion.SubMenuListener {
            @SuppressLint("ClickableViewAccessibility", "InflateParams")
            override fun onSubMenuClick(subMenu: SubMenu, viewGroup: ViewGroup) {
                when (subMenu.title) {
                    "Sales" -> {
                        navController.navigate(R.id.sales_graph, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    "Purchase" -> {
                        navController.navigate(R.id.purchase_graph, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    "Sales Invoice" -> {
                        navController.navigate(R.id.salesInvoice, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    "Sales Delivery" -> {
                        navController.navigate(R.id.sales_graph, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    "Purchase Invoice" -> {
                        navController.navigate(R.id.purchaseInvoice, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    "Purchase Delivery" -> {
                        navController.navigate(R.id.purchase_graph, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    "Customer Care" -> {

                        viewGroup.setOnTouchListener { _, event ->
                            when (event.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    xOff = event.x
                                    yOff = event.y
                                }
                            }
                            false
                        }


                        val popup = CustomerCarePopup(
                            layoutInflater.inflate(R.layout.popup_help, null),
                            viewGroup,
                            xOff, yOff
                        )

                        binding.drawerLayout.closeDrawer(GravityCompat.START)

                        popup.setOnCustomerPopupCallback(object : CustomerPopupCallback {
                            override fun onCustomerCareClick(value: String) {
                                when (value) {
                                    "WhatsApp" -> {
                                        if (hasContactPermission && hasReadPermission) {
                                            val message = "Hello I need Help"
                                            CoroutineScope(Dispatchers.Main).launch {
                                                try {
                                                    val whatsApp = Intent(Intent.ACTION_VIEW).also {
                                                        it.data = Uri.parse(
                                                            "http://api.whatsapp.com/send?phone=" +
                                                                    phoneNo +
                                                                    "&text=" +
                                                                    message
                                                        )
                                                    }
                                                    startActivity(whatsApp)
                                                } catch (exception: Exception) {
                                                    Log.i(
                                                        "TAG",
                                                        "onCustomerCareClick:${exception.message} "
                                                    )
                                                }
                                            }

                                        } else {
                                            contactPermissionLauncher.launch(
                                                arrayOf(
                                                    Manifest.permission.WRITE_CONTACTS,
                                                    Manifest.permission.READ_CONTACTS,
                                                )
                                            )

                                        }
                                    }


                                    "Call" -> {
                                        val intent = Intent(
                                            Intent.ACTION_DIAL,
                                            Uri.fromParts("tel", phoneNo, null)
                                        )
                                        startActivity(intent)
                                    }


                                }
                            }
                        })

                    }

                    "Customer Balance" -> {
                        navController.navigate(R.id.customerBalance, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }


                    "Customer Statement" -> {
                        navController.navigate(R.id.customerStatement, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }

                    "Product Wise Sales" -> {
                        navController.navigate(R.id.productWiseSales, null, navOption())
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }

                }

            }
        })


    }


    private fun navOption(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.image_in)
            .setExitAnim(R.anim.image_out)
            .setPopEnterAnim(R.anim.image_in)
            .setPopExitAnim(R.anim.image_out)
            .build()

    }


    private fun setUpDrawerLayout() {

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home3
            ),
            binding.drawerLayout
        )

        setSupportActionBar(binding.toolBar)
        setupWithNavController(binding.navigationView, navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setUpRecyclerView() {

        menuAdapter = MenuAdapter(this)
        binding.navRecyclerView.apply {
            adapter = menuAdapter
            layoutManager = LinearLayoutManager(applicationContext)

        }


    }


    private fun setMenuItems() {

        val salesList = arrayListOf(
            SubMenu("Sales", R.drawable.icon_sale),
            SubMenu("Sales Invoice", R.drawable.icon_sales_invoice),
            SubMenu("Sales Delivery", R.drawable.icon_delivery),
        )

        val purchaseList = arrayListOf(
            SubMenu("Purchase", R.drawable.icon_purchase),
            SubMenu("Purchase Invoice", R.drawable.icon_sales_invoice),
            SubMenu("Purchase Delivery", R.drawable.icon_delivery),
        )

        val reportList = arrayListOf(
            SubMenu("Customer Balance", R.drawable.icon_customer_balance),
            SubMenu("Customer Statement", R.drawable.icon_statement),
            SubMenu("Product Wise Sales", R.drawable.icon_product)
        )
        val helpList = arrayListOf(
            SubMenu("Customer Care", R.drawable.icon_customer_care)
        )


        val list: MutableList<Menu> = mutableListOf(
            Menu("Home", R.drawable.icon_home),
            Menu("Sales", R.drawable.icon_sale, salesList, true),
            Menu("Purchase", R.drawable.icon_purchase, purchaseList, true),
            Menu("Reports", R.drawable.icon_report, reportList, true),
            Menu("Receipt", R.drawable.icon_receipt),
            Menu("Product", R.drawable.icon_setting),
            Menu("Help", R.drawable.icon_help, helpList, true),
            Menu("Settings", R.drawable.icon_setting)
        )


        menuAdapter.differ.submitList(list)

    }


    override fun onMenuClickListener(menu: Menu) {
        when (menu.title) {
            "Settings" -> {
                navController.navigate(R.id.settings, null, navOption())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            "Home" -> {
                navController.navigate(R.id.home3, null, navOption())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            "Receipt" -> {
                navController.navigate(R.id.receipt, null, navOption())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            "Product" -> {
                navController.navigate(R.id.products, null, navOption())
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }


    private fun addNewContact() {

        CoroutineScope(Dispatchers.Main).launch {

            if (!Contact.ifContactExists(this@MainActivity, phoneNo)) {
                Contact.saveContact(this@MainActivity)
            }

        }

    }


}