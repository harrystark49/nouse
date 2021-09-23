package com.example.mini_proect.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mini_proect.DataBase.All_Devices_Entity
import com.example.mini_proect.DataBase.All_Devices_view_Model
import com.example.mini_proect.R
import com.example.mini_proect.fragments.Adapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    private lateinit var viewModel: All_Devices_view_Model
    lateinit var devicesList: List<All_Devices_Entity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        var edit = sharedPreferences.edit()



        var isLogged = sharedPreferences.getBoolean("isLogged",false)
        var AdminOrEmployee = sharedPreferences.getString("AdminOrEmployee",null)



        project_title.animate().setDuration(1000).rotationYBy(360f)
        app_title.alpha = 0f
        app_title.animate().setDuration(3000).alpha(1f).withEndAction {

            if(!isLogged) {
                var intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()
            }else if(AdminOrEmployee.equals("Employee")){
                startActivity(Intent(this,Home_screen_employee::class.java))
            }else{
                startActivity(Intent(this,Home_screen_admin::class.java))
            }
        }



        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(All_Devices_view_Model::class.java)

        viewModel.getLoginDetails(this)?.observe(this, Observer {
            if(it.isNotEmpty()){
            devicesList = it
            Adapter(this,devicesList)
        }else{
            Toast.makeText(this,"No Device Available",Toast.LENGTH_SHORT).show()
            }
        })


    }
}









