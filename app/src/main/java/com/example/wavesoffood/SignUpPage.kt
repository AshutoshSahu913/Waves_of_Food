package com.example.wavesoffood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wavesoffood.DataClass.UserModel
import com.example.wavesoffood.databinding.ActivitySignUpPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignUpPage : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient


    private val binding: ActivitySignUpPageBinding by lazy {
        ActivitySignUpPageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id)).requestEmail().build()

        //initialization firebase Auth
        auth = Firebase.auth

        //initialization firebase Database
        database = Firebase.database.reference

        //initialization Firebase Database
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.createAccountBtn.setOnClickListener {
            userName = binding.inputName.text.toString()
            email = binding.inputEmail.text.toString().trim()
            password = binding.inputPassword.text.toString().trim()

            if (!(userName.isBlank() || email.isBlank() || password.isBlank())) {
                createAccount(email, password)
                Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()

                if (userName.isBlank()) {
                    binding.inputName.requestFocus()
                    binding.inputName.setBackgroundResource(R.drawable.edittextshape_error)
                }
                if (email.isBlank()) {
                    binding.inputEmail.setBackgroundResource(R.drawable.edittextshape_error)
                }
                if (password.isBlank()) {
                    binding.inputPassword.setBackgroundResource(R.drawable.edittextshape_error)
                }
            }


        }


        binding.alreadyHaveBtn.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }

        binding.googleBtn.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }

    }

    //launcher for google sign
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign-In Successful", Toast.LENGTH_SHORT).show()
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    if (task.isSuccessful) {
                        startActivity(Intent(this, HomePage::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Sign in field", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Sign in field", Toast.LENGTH_SHORT).show()
            }

        }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()

                //save user data
                saveUserData()
                startActivity(Intent(this, LoginPage::class.java))
                finish()
            } else {
                Toast.makeText(this, "Account Creation Filed ", Toast.LENGTH_SHORT).show()
                Log.d("Account", "CreateAccount : Failure", task.exception)
            }

        }
    }


    private fun saveUserData() {
        //retrieve data from input filed

        userName = binding.inputName.text.toString()
        email = binding.inputEmail.text.toString().trim()
        password = binding.inputPassword.text.toString().trim()

        val user = UserModel(userName, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}