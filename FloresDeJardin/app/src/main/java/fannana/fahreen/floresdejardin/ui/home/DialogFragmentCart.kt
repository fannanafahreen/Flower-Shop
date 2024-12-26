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
import fannana.fahreen.floresdejardin.repository.shared_pref.PrefUtils
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import fannana.fahreen.floresdejardin.viewmodel.OrderViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DialogFragmentCart : DialogFragment(), OrderCartAdapter.OrderDraftListener {

    private lateinit var binding: DialogFragmentCartBinding

    private var productCarts: MutableList<ProductCart> = mutableListOf()

    private val orderViewModel: OrderViewModel by viewModels()

    private var totBill = 0.0
    private var finalBill = 0.0

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

        binding = DialogFragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {

            getDraftOrderList()

            btnClose.setOnClickListener {
                dialog?.dismiss()
            }

            /*if (cbPaymentOptions.isChecked) {
                layoutPaymentOptions.visibility =View.VISIBLE
            }*/
            tvPayMethod.text = "Payment Method - Not Selected)"

            ivBkashOption.setOnClickListener {
                ivBkashSelected.visibility = View.VISIBLE
                ivNagadSelected.visibility = View.GONE
                tvCodSelected.visibility = View.GONE
                tvPayMethod.text = "Payment Method - bKash)"
            }
            ivNagadOption.setOnClickListener {
                ivBkashSelected.visibility = View.GONE
                ivNagadSelected.visibility = View.VISIBLE
                tvCodSelected.visibility = View.GONE
                tvPayMethod.text = "Payment Method - Nagad)"
            }
            tvCodOption.setOnClickListener {
                ivBkashSelected.visibility = View.GONE
                ivNagadSelected.visibility = View.GONE
                tvCodSelected.visibility = View.VISIBLE
                tvPayMethod.text = "Payment Method - Cash On Delivery)"
            }

            bottomAppBar.setOnClickListener {
                //
                val signedIn = PrefUtils.init(requireContext()).getStringData(PrefUtils.SIGNED_IN)

                val loggedIn = PrefUtils.init(requireContext()).getStringData(PrefUtils.LOGGED_IN)

                if (signedIn == "YES" || loggedIn == "YES") {
                    val dialog = DialogFragmentOrder()
                    dialog.show(parentFragmentManager, "")
                } else {
                    val dialog = DialogFragmentNotLoggedIn()
                    dialog.show(parentFragmentManager, "")
                }


            }

        }

        return root
    }

    private fun getDraftOrderList(){
        this.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                orderViewModel.doGetOrderDetails()
                orderViewModel.productCartDetails.collect { order->
                    //Log.d("TAG", "getDraftOrderList RecyclerView: $orders & $order")
                    productCarts.clear()
                    if(order.isNotEmpty()){
                        Log.e("TAG", "getDraftOrderList Size: ${order.size}" )
                        productCarts.addAll(order)
                    }
                    setRecyclerView(productCarts)
                    Log.d("TAG", "getDraftOrderList: $productCarts")
                }
            }
        }
    }

    private fun setRecyclerView(productCart: List<ProductCart>) {

        val adapter = OrderCartAdapter(productCart, requireActivity(), this)
        binding.rvOrderDraft.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvOrderDraft.isNestedScrollingEnabled = false
        binding.rvOrderDraft.adapter = adapter
        adapter.notifyDataSetChanged()

        totalCalculation(productCart)
    }

    @SuppressLint("SetTextI18n")
    private fun totalCalculation(product: List<ProductCart>){
        /*if (productCarts.size == 0) {
            binding.middlePart.visibility = View.GONE
            binding.bottomAppBar.visibility = View.GONE
            binding.emptyCart.visibility = View.VISIBLE
        } else {
            binding.middlePart.visibility = View.VISIBLE
            binding.bottomAppBar.visibility = View.VISIBLE
            binding.emptyCart.visibility = View.GONE
        }*/
        binding.middlePart.visibility = View.VISIBLE
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.emptyCart.visibility = View.GONE

        for (p in product){
            val bill : Float = p.prodPrice?.times(p.prodQty!!) ?: 0.0f
            totBill += bill
        }
        binding.apply {
            tvProductsPrice.text = "$totBill TK"

            if (totBill > 99.00) {
                tvDelCharge.text = "0.00 BDT"
                finalBill = totBill
            } else {
                tvDelCharge.text = "30.00 BDT"
                finalBill = totBill + 30.00 ?: 0.0
            }
            tvFinalTotal.text = "$finalBill BDT"
            tvTotalBill.text = "(Total Bill $finalBill BDT || "
        }
    }

    override fun onOrderDraftEditClick(productCartDraft: ProductCart, pos: Int) {
        //
    }

    override fun onOrderDraftDeleteClick(productCartDraft: ProductCart, pos: Int) {
        //
    }
}