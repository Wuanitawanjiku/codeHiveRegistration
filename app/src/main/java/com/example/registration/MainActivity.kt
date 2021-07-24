package com.example.registration

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.registration.api.ApiClient
import com.example.registration.api.ApiInterface
import com.example.registration.models.RegistrationRequest
import com.example.registration.models.RegistrationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etDob: EditText
    lateinit var spNationality: Spinner
    lateinit var etPhoneNumber: EditText
    lateinit var etEmail: EditText
    lateinit var btnRegister: Button
    lateinit var etPassword: EditText

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        castViews()
        clickRegister()
    }

    fun castViews(){
        etName = findViewById(R.id.etName)
        etDob  = findViewById(R.id.etDob)
        spNationality = findViewById(R.id.spNationality)
        etEmail = findViewById(R.id.etEmail)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        btnRegister = findViewById(R.id.btnRegister)
        etPassword = findViewById(R.id.etPassword)

        val nationality = arrayOf("Kenyan", "Ugandan", "Rwandese", "South Sudanes")
        var nationalityAdapter = ArrayAdapter<String>(baseContext, android.R.layout.simple_spinner_item, nationality)
        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNationality.adapter = nationalityAdapter

    }
    fun clickRegister(){
        var error = false
        btnRegister.setOnClickListener {
            var name = etName.text.toString()
            if (name.isEmpty()) {
                error = true
                etName.setError("Name is required")
            }

            var dob = etDob.text.toString()
            if (dob.isEmpty()) {
                error = true
                etDob.setError("Date of birth is required")
            }

            var tilPassword = etPassword.text.toString()
            if (tilPassword.isEmpty()) {
                error = true
                etPassword.setError("Input password")
            }

            var nationality = spNationality.selectedItem.toString()

            var email = etEmail.text.toString()
            if (email.isEmpty()) {
                error = true
                etEmail.setError("Email required")
            }

            var phoneNumber = etPhoneNumber.text.toString()
            if (phoneNumber.isEmpty()) {
                error = true
                etPhoneNumber.setError("Input phone number")
            }

            var registrationRequest = RegistrationRequest(
                name = name,
                phoneNumber = phoneNumber,
                email = email,
                nationality = nationality.uppercase(),
                dateOfBirth = dob,
                password = tilPassword
            )

            val retrofit = ApiClient.buildApiClient(ApiInterface::class.java)
            var request = retrofit.registerStudent(registrationRequest)
            request.enqueue(object : Callback<RegistrationResponse> {

                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
                }

            })
            val intent = Intent(baseContext, LogIn::class.java)
            startActivity(intent)
        }
}
}

