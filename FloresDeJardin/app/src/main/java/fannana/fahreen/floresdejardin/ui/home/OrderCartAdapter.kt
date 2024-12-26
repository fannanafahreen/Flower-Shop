package fannana.fahreen.floresdejardin.ui.home


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fannana.fahreen.floresdejardin.R
import fannana.fahreen.floresdejardin.databinding.OrderedItemBinding
import fannana.fahreen.floresdejardin.databinding.OrderedItemNewBinding
import fannana.fahreen.floresdejardin.ui.commons.ProductCart


class OrderCartAdapter(
    private val productCartList: List<ProductCart>,
    private val context: Context,
    private val listener: OrderDraftListener
) : RecyclerView.Adapter<OrderCartAdapter.ReportViewHolder>() {

    var orderQty = 0
    var subTotal = 0.0f
    var totalPrice = 0.0f


    interface OrderDraftListener{
        fun onOrderDraftEditClick(productCartDraft: ProductCart, pos :Int)
        fun onOrderDraftDeleteClick(productCartDraft: ProductCart, pos :Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ordered_item_new, parent, false)
        return ReportViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ReportViewHolder , position: Int) {
        val orderDraft = productCartList[position]

        val prodName  = orderDraft.prodName
        val itemPrice = orderDraft.prodPrice

        orderQty = orderDraft.prodQty ?: 1

        subTotal = orderQty * itemPrice!!


        holder.binding.apply {
            tvProductName.text  = prodName
            tvProductPrice.text = itemPrice.toString()
            tvOrderQty.text = orderQty.toString()
            tvSubTotal.text  = subTotal.toString()

            /*btnPlus.setOnClickListener {

                orderQty++

                tvOrderQty.text = orderQty.toString()

                totalPrice = orderQty * itemPrice

                tvProductPrice.text = "Subtotal : $totalPrice"

                orderDraft.prodQty = orderDraft.prodQty!! + 1

                orderDraft.prodPrice = totalPrice

                Log.d("TAG", "Click Checking: $orderQty & $totalPrice")
            }*/

            /*btnMinus.setOnClickListener {
                if (orderQty >= 1) {

                    orderQty--

                    tvOrderQty.text = orderQty.toString()

                    totalPrice = orderQty * itemPrice

                    tvProductPrice.text = "Subtotal : $totalPrice"
                } else {
                    Log.d("TAG", "Click Checking: $orderQty & $totalPrice")
                }
            }*/

        }
    }

    override fun getItemCount(): Int {
        return productCartList.size
    }

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = OrderedItemNewBinding.bind(itemView)
    }

}
