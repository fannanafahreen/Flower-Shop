package fannana.fahreen.floresdejardin.ui.flower_juice_list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fannana.fahreen.floresdejardin.R
import fannana.fahreen.floresdejardin.databinding.FragmentListOfProductsBinding
import fannana.fahreen.floresdejardin.ui.commons.DialogFragmentProductAdd
import fannana.fahreen.floresdejardin.ui.commons.Product
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import fannana.fahreen.floresdejardin.ui.commons.DialogFragmentProductDetails
import fannana.fahreen.floresdejardin.viewmodel.DataStoreViewModel
import fannana.fahreen.floresdejardin.viewmodel.OrderViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint

class FlowerJuiceListFragment: Fragment(),  FlowerJuiceListAdapter.FlowerJuiceListener {

    private lateinit var binding: FragmentListOfProductsBinding

    private var juiceListClone: MutableList<Product> = mutableListOf()

    private var juiceList: MutableList<Product> = mutableListOf()

    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    private val orderViewModel: OrderViewModel by viewModels()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dummyData()
        setRecyclerView(juiceList)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            tvFragmentTitle.text = "Flower juice"

            svProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchProduct(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchProduct(newText)
                    return false
                }
            })

        }

        return root
    }

    //Adding dummy data into list
    private fun dummyData() {

        juiceList.add(Product(131,"P10131","Rose Juice", 50.0, R.mipmap.juice_rose))
        juiceList.add(Product(133,"P10133","Mint Leaf Juice", 25.0, R.mipmap.juice_mint_leaf))

    }

    private fun searchProduct(query: String?) {
        val query1 = query?.lowercase(Locale.getDefault())
        juiceListClone.clear()

        for (j in juiceList){
            val fName = j.name.lowercase(Locale.getDefault())

            if (query1?.let { fName.contains(it) } == true){
                juiceListClone.add(j)
            }
        }
        setRecyclerView(juiceListClone)
    }

    //Calling FlowerListAdapter
    private fun setRecyclerView(juiceList: MutableList<Product>) {


        val activity = activity
        if (activity != null) {
            val adapter = FlowerJuiceListAdapter(juiceList, activity, this)
            binding.rvProductList.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvProductList.isNestedScrollingEnabled = false
            binding.rvProductList.adapter = adapter
        }
    }

    override fun onFlowerJuiceClick(flowerjuice: Product, position: Int) {
//        Log.d("TAG", "onPlantClick: === 1")
        val dialog = DialogFragmentProductDetails()
        dialog.init(flowerjuice)
        dialog.show(parentFragmentManager, "")
    }

    override fun onProductAddClick(flowerjuice: Product , pos: Int) {

        val dialog = DialogFragmentProductAdd()
        dialog.init(flowerjuice)
        dialog.show(parentFragmentManager, "")
        /*binding.apply {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.confirmation_alert)
            builder.setMessage(R.string.confirmation_msg)
            builder.setPositiveButton(R.string.confirm) { _ , _ ->
                checkDuplicateProduct(product)
            }
            builder.setNegativeButton(R.string.cancel) { dialog , _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }*/
    }

    private fun checkDuplicateProduct(product : Product) {
        this.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                orderViewModel.doGetOrderDetails(product.prodCode ?: "").collect {
                    if (it?.prodID != product.prodID) {
                        saveProductInCart(product)
                    } else {
                        //Toast.makeText(requireContext(),"This item is already added to cart!!", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "getDraftOrderList: ${product.name} && ${it?.prodID} [Repeated]")
                    }
                }
            }
        }
    }

    private fun saveProductInCart(product : Product){
        val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy")
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        val productCart = ProductCart()
        productCart.prodID     = product.prodID
        productCart.prodCode   = product.prodCode
        productCart.prodName   = product.name
        productCart.prodPrice  = product.price.toFloat()
        productCart.prodQty    = 1

        //Save the details to room database
        orderViewModel.insertOrderDetails(productCart)

        orderViewModel.response.observe(this){

            dataStoreViewModel.setSavedKey(true)

        }

    }

}
