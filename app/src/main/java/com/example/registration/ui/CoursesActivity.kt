package com.example.registration.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.Constants
import com.example.registration.databinding.ActivityCoursesBinding
import com.example.registration.viewmodel.CoursesViewModel

class CoursesActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoursesBinding
    lateinit var sharedPreferences: SharedPreferences
    val coursesViewModel: CoursesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFS, Context.MODE_PRIVATE)
    }

    override fun onResume(){
        super.onResume()
        var accessToken = sharedPreferences.getString(Constants.toString(),"ACCESS_TOKEN")
//        var accessToken = sharedPreferences.getString(Constants.ACCESSTOKEN, Constants.EMPTY_STRING)
        var bearer = "Bearer $accessToken"

        //Log user out if access token is empty
        //how to read shared preferences from the adapter
        if (accessToken!!.isNotEmpty()){
        coursesViewModel.coursesList(accessToken)
        }
        else{
            startActivity(Intent(baseContext, LogIn::class.java))
        }
        var rvCoursesResponseAdapter = binding.rvCourses
        rvCoursesResponseAdapter.layoutManager = LinearLayoutManager(baseContext)
        coursesViewModel.coursesLiveData.observe(this, {coursesList->

            var coursesResponseAdapter = CoursesResponseAdapter(coursesList)
            rvCoursesResponseAdapter.adapter = coursesResponseAdapter
           Toast.makeText(baseContext, "${coursesList.size} courses fetched", Toast.LENGTH_LONG).show()
        })
        coursesViewModel.coursesFailedLiveData.observe(this, {
            error->Toast.makeText(baseContext, error, Toast.LENGTH_LONG).show()
        })
    }
}











//displayContent()

//fun displayContent(){
//        var rvCourses = findViewById<RecyclerView>(R.id.rvCourses)
//        var courseList = listOf(
//            Course("MB101", "Mobile Development", "Introduction to Android Development with Kotlin", "John Owuor"),
//            Course("BE2101", "Backend Development", "Introduction to Backend Development with Python", "James Mwai"),
//            Course("IOT104", "IoT", "IoT implementation", "Barre Yassin"),
//            Course("FE105", "Frontend Development", "Introduction to Frontend Development with JavaScript", "Purity Maina"),
//            Course("IDX502", "Industrial Design", "Design for everyday life", "Erick Asiago"),
//            Course("UXR301", "UX Research", "UX and UI field research", "Joy Wambui")
//        )
//        var coursesAdapter = CoursesAdapter(courseList)
//        rvCourses.layoutManager = LinearLayoutManager(baseContext)
//        rvCourses.adapter = coursesAdapter
//    }