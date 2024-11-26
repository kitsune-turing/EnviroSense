package com.kitsune.IUPB.android.envirosense.ui.view


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kitsune.IUPB.android.envirosense.R
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodel.RegisterViewModel
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodelfactory.RegisterViewModelFactory
import com.kitsune.IUPB.android.envirosense.data.session.SessionManager
import com.kitsune.IUPB.android.envirosense.data.repository.UserRepository


class RegisterActivity : AppCompatActivity() {
    private lateinit var inputName: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(UserRepository(FirebaseAuth.getInstance()))
    }
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views and session manager
        inputName = findViewById(R.id.input_name)
        inputEmail = findViewById(R.id.input_email)
        inputPassword = findViewById(R.id.input_password)
        inputConfirmPassword = findViewById(R.id.input_confirm_password)
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)
        sessionManager = SessionManager(this)


        // Register button listener
        btnRegister.setOnClickListener {
            val name = inputName.text.toString()
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            val confirmPassword = inputConfirmPassword.text.toString()


            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this, "Elementos clear", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!email.contains("@")){
                Toast.makeText(this, "Email no contain @", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            registerViewModel.registerUser(name, email, password) { isSuccess, user ->
                if (isSuccess && user != null) {
                    sessionManager.saveUserSession(user.uid, user.email ?: "")
                    val intent = Intent(this, SelectorActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Login button listener
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}