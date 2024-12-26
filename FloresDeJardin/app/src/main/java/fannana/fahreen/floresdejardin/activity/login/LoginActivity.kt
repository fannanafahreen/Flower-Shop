package fannana.fahreen.floresdejardin.activity.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import fannana.fahreen.floresdejardin.activity.main.MainActivity
import fannana.fahreen.floresdejardin.activity.signup.SignUpActivity
import fannana.fahreen.floresdejardin.databinding.ActivityLoginBinding
import fannana.fahreen.floresdejardin.repository.shared_pref.PrefUtils


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    //private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //firebaseAuth = FirebaseAuth.getInstance()
        /*binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }*/

        binding.btnLogin.setOnClickListener {
            val email = binding.etUserName.text.toString()
            val pass = binding.etPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                /*firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        PrefUtils.init(this).saveStringData(PrefUtils.LOGGED_IN, "YES")

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }*/
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()

        /*if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/
    }
}