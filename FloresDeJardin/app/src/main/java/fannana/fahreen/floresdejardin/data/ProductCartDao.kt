package fannana.fahreen.floresdejardin.data

import androidx.room.*
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCartDao {

    /*CREATE*/
    //insert data to room database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToRoomDatabase(productCart: ProductCart) : Long

    /*READ*/
    //select (select cusCode, cusName, cusAddress, invoiceDate, count (*) from product_table where orderId = o.invoiceNo) totalItem from order_table o
    //get all order inserted to room database...normally this is supposed to be a list of orders
    /*@Transaction
    @Query("SELECT * FROM order_table ORDER BY cusName ASC")//DESC
    fun getOrderDetails() : Flow<List<Order>>*/

    @Transaction
    @Query("select prodID, prodCode, prodName, prodPrice, prodQty from cart_table o")//DESC
    fun getOrderDetails() : Flow<List<ProductCart>>

    //Get single order inserted to room database
    @Transaction
    @Query("SELECT * FROM cart_table WHERE prodID = :id ORDER BY prodName DESC")
    fun getSingleOrderDetails(id: Long) : Flow<ProductCart>

    @Transaction
    @Query("SELECT * FROM cart_table WHERE prodCode = :id ")
    fun getOrderByCustId(id: String) : Flow<ProductCart>

    @Update
    suspend fun updateOrderQtyRecord(productCart: ProductCart)

    /*DELETE*/
    //Delete single order details:
    @Query("DELETE FROM cart_table WHERE prodID = :id")
    suspend fun deleteSingleOrderDetails(id: Int) : Int

    //Delete all order details
    @Query("DELETE FROM cart_table")
    suspend fun deleteAllOrderDetails() : Int

}