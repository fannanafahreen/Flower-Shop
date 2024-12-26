package fannana.fahreen.floresdejardin.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import android.util.Log
import fannana.fahreen.floresdejardin.data.ProductCartDao
import fannana.fahreen.floresdejardin.ui.commons.ProductCart

class OrderRepo @Inject constructor(private val orderDao: ProductCartDao) {
    //insert order details to room
    suspend fun createOrderRecords(productCart: ProductCart) : Long {
        return orderDao.insertToRoomDatabase(productCart)
    }

    //get single order details e.g with id 1
    val getProductCartDetails: Flow<List<ProductCart>> get() =  orderDao.getOrderDetails()
    fun getOrderDetailsByCustID(custId : String) : Flow<ProductCart?> {
        return orderDao.getOrderByCustId(custId)
    }

    //Delete:
    //delete single order record
    suspend fun deleteSingleOrderRecord(invId: Int) {
        orderDao.deleteSingleOrderDetails(invId)
        Log.d("TAG", "deleteSingleOrder Repo: $invId")
    }

    //Delete all order record
    suspend fun deleteAllOrderRecord() {
        orderDao.deleteAllOrderDetails()
    }
}