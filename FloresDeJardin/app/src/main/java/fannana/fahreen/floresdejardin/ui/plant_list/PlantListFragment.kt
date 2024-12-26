package fannana.fahreen.floresdejardin.ui.plant_list

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
class PlantListFragment : Fragment(), PlantListAdapter.PlantListener {

    private lateinit var binding: FragmentListOfProductsBinding
    private var plantList :MutableList<Product> = mutableListOf()

    private var plantListClone :MutableList<Product> = mutableListOf()

    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    private val orderViewModel: OrderViewModel by viewModels()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentListOfProductsBinding.inflate(inflater,container,false)
        val root : View= binding.root

        dummyData()
        setRecyclerView(plantList)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            tvFragmentTitle.text = "Plants"

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
    private fun dummyData(){


        plantList.add(Product(141,"P10141","Easter Plant",50.0, R.mipmap.plant_easter ))
        plantList.add(Product(142,"P10142","Tomato Plant",150.0,R.mipmap.plant_tommato))
        plantList.add(Product(143,"P10143","Lili Plant",75.0,R.mipmap.plant_lili))

    }

    private fun searchProduct(query: String?) {
        val query1 = query?.lowercase(Locale.getDefault())
        plantListClone.clear()

        for (j in plantList){
            val fName = j.name.lowercase(Locale.getDefault())

            if (query1?.let { fName.contains(it) } == true){
                plantListClone.add(j)
            }
        }
        setRecyclerView(plantListClone)
    }

    //Calling PlantListAdapter
    private fun setRecyclerView(plantList: MutableList<Product>) {

        val activity = activity
        if(activity != null){
            val adapter = PlantListAdapter(plantList, activity, this)
            binding.rvProductList.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvProductList.isNestedScrollingEnabled = false
            binding.rvProductList.adapter = adapter
        }

    }

    override fun onPlantClick(plant: Product, position: Int) {
//        Log.d("TAG", "onPlantClick: === 1")
        val dialog = DialogFragmentProductDetails()
        dialog.init(plant)
        dialog.show(parentFragmentManager, "")
    }

    override fun onProductAddClick(plant: Product , pos: Int) {
        val dialog = DialogFragmentProductAdd()
        dialog.init(plant)
        dialog.show(parentFragmentManager, "")
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