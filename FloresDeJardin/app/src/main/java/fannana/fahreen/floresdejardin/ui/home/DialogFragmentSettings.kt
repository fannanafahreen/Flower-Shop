package fannana.fahreen.floresdejardin.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fannana.fahreen.floresdejardin.R
import fannana.fahreen.floresdejardin.databinding.DialogFragmentCartBinding
import fannana.fahreen.floresdejardin.databinding.DialogFragmentProfileBinding
import fannana.fahreen.floresdejardin.databinding.DialogFragmentSettingsBinding
import fannana.fahreen.floresdejardin.repository.shared_pref.PrefUtils
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import fannana.fahreen.floresdejardin.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.dialog_fragment_profile.btn_profile
import kotlinx.android.synthetic.main.dialog_fragment_settings.btn_change_pass
import kotlinx.android.synthetic.main.dialog_fragment_settings.btn_profile
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DialogFragmentSettings : DialogFragment() {

    private lateinit var binding: DialogFragmentSettingsBinding


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

        binding = DialogFragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {


            btnClose.setOnClickListener {
                dialog?.dismiss()
            }

            btnProfile.setOnClickListener {
                val dialog = DialogFragmentProfile()
                dialog.show(parentFragmentManager, "")
            }

            btnChangePass.setOnClickListener {
                val dialog = DialogFragmentChangePass()
                dialog.show(parentFragmentManager, "")
            }

        }

        return root
    }

}
