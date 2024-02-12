package com.example.wavesoffood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wavesoffood.DataClass.UserModel
import com.example.wavesoffood.databinding.ActivityFirstBinding
import com.example.wavesoffood.databinding.ActivityLoginPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class LoginPage : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginPageBinding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id)).requestEmail().build()

        //Initialization of Firebase Auth
        auth = Firebase.auth

        //Initialization of Firebase Database
        database = Firebase.database.reference

        //Initialization of Google
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        binding.goSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpPage::class.java))
        }
        binding.loginBtn.setOnClickListener {

            //get data from text Filed
            email = binding.inputEmail.text.toString().trim()
            password = binding.inputPassword.text.toString().trim()

            binding.inputEmail.setBackgroundResource(R.drawable.edittextshape)
            binding.inputPassword.setBackgroundResource(R.drawable.edittextshape)


            if (!(email.isBlank() || password.isBlank())) {
                createUser()
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                binding.loginBtn.setBackgroundResource(R.drawable.un_shape)
                startActivity(Intent(this, LocationActivity::class.java))
            } else {
                if (email.isBlank()) {
                    binding.inputEmail.requestFocus()
                    binding.inputEmail.setBackgroundResource(R.drawable.edittextshape_error)
                }
                if (password.isBlank()) {
                    binding.inputPassword.requestFocus()
                    binding.inputPassword.setBackgroundResource(R.drawable.edittextshape_error)
                }
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.googleBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)

        }
    }

    //launcher for Google sign-in
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

    private fun createUser() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserData()
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(this, "Sign in Filed 🤡", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun saveUserData() {
        //get data from text Filed
        email = binding.inputEmail.text.toString().trim()
        password = binding.inputPassword.text.toString().trim()

        val user = UserModel(email = email, password = password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // sava data in to data
        database.child("user").child(userId).setValue(user)

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
        finish()
    }
}