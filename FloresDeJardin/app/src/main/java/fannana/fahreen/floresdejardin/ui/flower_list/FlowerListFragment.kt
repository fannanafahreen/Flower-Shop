package fannana.fahreen.floresdejardin.ui.flower_list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fannana.fahreen.floresdejardin.R
import fannana.fahreen.floresdejardin.databinding.FragmentListOfProductsBinding
import fannana.fahreen.floresdejardin.ui.commons.Product
import fannana.fahreen.floresdejardin.ui.commons.DialogFragmentProductDetails
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fannana.fahreen.floresdejardin.ui.commons.DialogFragmentProductAdd
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import fannana.fahreen.floresdejardin.viewmodel.DataStoreViewModel
import fannana.fahreen.floresdejardin.viewmodel.OrderViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FlowerListFragment: Fragment(), FlowerListAdapter.FlowerListener {

        private lateinit var binding: FragmentListOfProductsBinding

        private var flowerList: MutableList<Product> = mutableListOf()

        private var flowerListClone: MutableList<Product> = mutableListOf()

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
                setRecyclerView(flowerList)
                //setRecyclerView(flowerListClone)

                binding.apply {
                        btnBack.setOnClickListener {
                                findNavController().navigateUp()
                        }

                        tvFragmentTitle.text = "Flowers"

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

                flowerList.add(Product(121,"P10121","Rose",50.0, R.mipmap.img_flower_rose ))
                flowerList.add(Product(122,"P10122","Lili",70.0,R.mipmap.lili))
                flowerList.add(Product(123,"P10123","Lavender",90.0,R.mipmap.img_flower_lavender))
                flowerList.add(Product(124,"P10124","Zinnia",60.0,R.mipmap.zinnia))
                flowerList.add(Product(125,"P10125","Gladiolus",90.0,R.mipmap.gladiolus))
                flowerList.add(Product(126,"P10126","Flax",40.0, R.mipmap.flax))
                flowerList.add(Product(127,"P10127","Magnolia",80.0,R.mipmap.magnolia))
                flowerList.add(Product(128,"P10128","Marigold",60.0,R.mipmap.marigold))
                flowerList.add(Product(129,"P10129","Daisy",90.0,R.mipmap.daisy))

        }

        private fun searchProduct(query: String?) {
                val query1 = query?.lowercase(Locale.getDefault())
                flowerListClone.clear()

                for (f in flowerList){
                        val fName = f.name.lowercase(Locale.getDefault())

                        if (query1?.let { fName.contains(it) } == true){
                                flowerListClone.add(f)
                        }
                }
                setRecyclerView(flowerListClone)
        }

        //Calling FlowerListAdapter
        private fun setRecyclerView(flowerList: MutableList<Product>) {

                val activity = activity
                if(activity != null){
                        val adapter = FlowerListAdapter(flowerList, activity, this)
                        binding.rvProductList.layoutManager = LinearLayoutManager(requireActivity())
                        binding.rvProductList.isNestedScrollingEnabled = false
                        binding.rvProductList.adapter = adapter
                }
        }

        override fun onFlowerClick(flower: Product, position: Int) {
                val dialog = DialogFragmentProductDetails()
                dialog.init(flower)
                dialog.show(parentFragmentManager, "")
//                Log.d("TAG", "Clicked Flower Position: $position - ${flower.name}")
        }

        override fun onProductAddClick(flower: Product , pos: Int) {
                val dialog = DialogFragmentProductAdd()
                dialog.init(flower)
                dialog.show(parentFragmentManager, "")
        }

        private fun checkDuplicateProduct(product : Product) {
                this.lifecycleScope.launch {
                        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                                orderViewModel.doGetOrderDetails(product.prodCode ?: "").collect {
                                        if (it?.prodID != product.prodID) {
                                                saveProductInCart(product)
                                        } else {
                                                //Toast.makeText(requireContext(),"This item is already added to cart!!",Toast.LENGTH_SHORT).show()
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

                        Log.d("TAG" , "saveOrderCustomerAsDraft: $it")

                        //Success, save key so on next visit user goes to details screen
                        dataStoreViewModel.setSavedKey(true)

                        //Show toast message
                        //Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                        //Toast.makeText(requireContext(), "Record Saved [$it]", Toast.LENGTH_SHORT).show()
                }

        }

}        

