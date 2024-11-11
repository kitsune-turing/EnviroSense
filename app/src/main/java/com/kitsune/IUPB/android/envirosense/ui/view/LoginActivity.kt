package com.kitsune.IUPB.android.envirosense.ui.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kitsune.IUPB.android.envirosense.R
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodel.LoginViewModel
import com.kitsune.IUPB.android.envirosense.ui.view.viewmodelfactory.LoginViewModelFactory
import com.kitsune.IUPB.android.envirosense.data.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kitsune.IUPB.android.envirosense.data.repository.UserRepository


class LoginActivity : AppCompatActivity() {
    private lateinit var inputUser: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnLoginGoogle: Button
    private lateinit var btnRegister: Button


    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(UserRepository(FirebaseAuth.getInstance()))
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9000
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        inputUser = findViewById(R.id.input_user)
        inputPass = findViewById(R.id.input_password)
        btnLogin = findViewById(R.id.btn_login)
        btnLoginGoogle = findViewById(R.id.btn_login_google)
        btnRegister = findViewById(R.id.btn_register)
        sessionManager = SessionManager(this)


        btnLogin.setOnClickListener {
            val email = inputUser.text.toString()
            val pass = inputPass.text.toString()


            loginViewModel.loginUser(email, pass) { isSuccess, user ->
                if (isSuccess) {
                    user?.let {
                        sessionManager.saveUserSession(it.uid, it.email ?: "")
                    }

                    val intent = Intent(this, SelectorActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnLoginGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)


            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val idToken = account.idToken
                    loginViewModel.loginWithGoogle(idToken ?: "") { isSuccess, user ->
                        if (isSuccess) {
                            user?.let {
                                sessionManager.saveUserSession(it.uid, it.email ?: "")
                            }
                            val intent = Intent(this, SelectorActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Google login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: ApiException) {
                Log.w("LoginActivity", "Google sign-in failed. Error code: ${e.statusCode}", e)
                Toast.makeText(this, "Google sign-in failed with error code: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}