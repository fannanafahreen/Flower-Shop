package fannana.fahreen.floresdejardin.activity.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import fannana.fahreen.floresdejardin.activity.login.LoginActivity
import fannana.fahreen.floresdejardin.databinding.ActivitySignUpBinding
import fannana.fahreen.floresdejardin.repository.shared_pref.PrefUtils
import fannana.fahreen.floresdejardin.ui.home.DialogFragmentOrder

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    //private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebaseAuth = FirebaseAuth.getInstance()

        /*binding.textView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }*/
        binding.btnSignup.setOnClickListener {
            val name = binding.etUserName.text.toString()
            val phone = binding.etUserPhone.text.toString()
            val address = binding.etUserAddress.text.toString()

            val email = binding.etUserMail.text.toString()
            val pass = binding.etPassword.text.toString()
            val confirmPass = binding.etPasswordConfirm.text.toString()

            val dialog = DialogFragmentOrder()
            dialog.show(supportFragmentManager, "")

            /*if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            //
                            PrefUtils.init(this).saveStringData(PrefUtils.PREF_USER_NAME, name)
                            PrefUtils.init(this).saveStringData(PrefUtils.PREF_USER_MOBILE, phone)
                            PrefUtils.init(this).saveStringData(PrefUtils.PREF_USER_ADDRESS, address)

                            PrefUtils.init(this).saveStringData(PrefUtils.SIGNED_IN, "YES")

                            //

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }*/
        }
    }
}