package fannana.fahreen.floresdejardin.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import fannana.fahreen.floresdejardin.repository.OrderRepo
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OrderViewModel @ViewModelInject constructor(private val orderRepo: OrderRepo): ViewModel() {

    //Insert order details...
    private val _response = MutableLiveData<Long>()
    val response: LiveData<Long> = _response

    //insert order details to room database
    fun insertOrderDetails(productCart: ProductCart){
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(orderRepo.createOrderRecords(productCart))
        }
    }

    //Launching a new coroutine to update an order qty.
    private fun updateOrderQty(productCart: ProductCart) {
        viewModelScope.launch(Dispatchers.IO) {
            //_response.postValue(orderRepo.updateOrderQty(order))
        }

    }

    //Increase item...
    fun addItemQtyIntoOrder(productCart: ProductCart) {
        productCart.prodQty = productCart.prodQty?.plus(1)
        updateOrderQty(productCart)
    }

    //Update order details to room database
    fun doUpdateOrderRecord(productCart: ProductCart){
        viewModelScope.launch(Dispatchers.IO) {
            //_response.postValue(orderRepo.updateOrderQtyBill(order))
        }
    }

    var productCartDetails = MutableStateFlow<List<ProductCart>>(emptyList())
    //val productCartDetails : StateFlow<List<ProductCart>> =  _productCartDetails

    fun doGetOrderDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepo.getProductCartDetails
                .catch { e->
                    //Log error here
                }
                .collect {
                    Log.e("TAG", "doGetOrderDetails --->>>>>>>>>: ${it.size}")
                    productCartDetails.value = it
                }
        }
    }

    fun doGetOrderDetails(custId: String): MutableStateFlow<ProductCart?> {
        val data = MutableStateFlow<ProductCart?>(ProductCart())
        viewModelScope.launch(Dispatchers.IO) {
            orderRepo.getOrderDetailsByCustID(custId)
                .catch { e ->
                    Log.e("TAG", "doGetOrderDetails: ", e)
                }
                .collect {
                    Log.e("TAG", "doGetOrderDetails->: ${it?.prodCode}")
                    data.value = it
                }
        }
        return data
    }

    //Delete single order record:
    fun doDeleteSingleOrderRecord(invId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepo.deleteSingleOrderRecord(invId)
            Log.d("TAG", "deleteSingleOrder VM: $invId")
        }
    }

    /*fun doDeleteAllOrderRecord(){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepo.deleteAllOrderRecord()
        }
    }*/

    fun doDeleteAllOrderRecord(){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepo.deleteAllOrderRecord()
        }
    }

}