package fannana.fahreen.floresdejardin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fannana.fahreen.floresdejardin.R
import fannana.fahreen.floresdejardin.databinding.FragmentHomeBinding
import fannana.fahreen.floresdejardin.repository.shared_pref.PrefUtils

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val ordered = PrefUtils.init(requireContext()).getStringData(PrefUtils.ORDERED)

        binding.apply {
            btnProfile.setOnClickListener {
                val dialog = DialogFragmentProfile()
                dialog.show(parentFragmentManager, "")
            }

            btnFlowerList.setOnClickListener {
                findNavController().navigate(R.id.nav_flower_list)
            }

            btnSettings.setOnClickListener {
                val dialog = DialogFragmentSettings()
                dialog.show(parentFragmentManager, "")
            }


        }

        binding.ivBtnTest.setOnClickListener {
            val dialog = DialogFragmentCart()
            dialog.show(parentFragmentManager, "")
        }

        binding.btnPlantList.setOnClickListener {
            findNavController().navigate(R.id.nav_plant_list)

        }

        binding.btnFlowerJuiceList.setOnClickListener {
            findNavController().navigate(R.id.nav_flowerjuice_list)

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}