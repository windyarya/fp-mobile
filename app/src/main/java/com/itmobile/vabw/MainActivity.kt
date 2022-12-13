package com.itmobile.vabw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import com.itmobile.vabw.maps.MapsActivity
import com.itmobile.vabw.colordetection.activities.MainActivity as CameraActivity

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var button2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.tesCD)
        button2 = findViewById(R.id.faskesTerdekat)
        button.setOnClickListener(listener)
        button2.setOnClickListener(listener)
    }

    val listener= View.OnClickListener { view ->
        when (view.getId()) {
            R.id.tesCD -> {
                val Intent = Intent(this, CameraActivity::class.java)
                startActivity(Intent)
            }
            R.id.faskesTerdekat -> {
                val Intent = Intent(this, MapsActivity::class.java)
                startActivity(Intent)
            }
        }
    }
}