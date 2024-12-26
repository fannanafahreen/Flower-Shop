package fannana.fahreen.floresdejardin.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fannana.fahreen.floresdejardin.R
import fannana.fahreen.floresdejardin.activity.login.LoginActivity
import fannana.fahreen.floresdejardin.activity.signup.SignUpActivity



@AndroidEntryPoint
class DialogFragmentNotLoggedIn : DialogFragment(){

    private lateinit var binding: DialogFragmentNotInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DialogFragmentNotInBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {

            btnLogin.setOnClickListener {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }

            btnCreateAccount.setOnClickListener {
                val intent = Intent(requireContext(), SignUpActivity::class.java)
                startActivity(intent)
            }




        }

        return root
    }


}