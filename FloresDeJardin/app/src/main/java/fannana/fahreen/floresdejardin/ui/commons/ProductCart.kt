package fannana.fahreen.floresdejardin.ui.commons

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
class ProductCart {
    @PrimaryKey(autoGenerate = true)
    var prodID    : Int?    = null
    var prodCode  : String? = null
    var prodName  : String? = null
    var prodPrice : Float? = null
    var prodQty   : Int?    = null
}