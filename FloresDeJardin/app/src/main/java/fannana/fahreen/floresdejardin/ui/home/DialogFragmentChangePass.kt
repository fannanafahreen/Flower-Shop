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
import fannana.fahreen.floresdejardin.databinding.DialogFragmentSettingsChangePassBinding
import fannana.fahreen.floresdejardin.repository.shared_pref.PrefUtils
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import fannana.fahreen.floresdejardin.viewmodel.OrderViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DialogFragmentChangePass : DialogFragment() {

    private lateinit var binding: DialogFragmentSettingsChangePassBinding


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

        binding = DialogFragmentSettingsChangePassBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {


            //

        }

        return root
    }

}
