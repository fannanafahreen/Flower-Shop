package fannana.fahreen.floresdejardin.ui.flower_juice_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fannana.fahreen.floresdejardin.R
import fannana.fahreen.floresdejardin.databinding.ItemProductBinding
import fannana.fahreen.floresdejardin.ui.commons.Product


class FlowerJuiceListAdapter (

    private val juiceList: MutableList<Product>,
    private val context : Context,
    private val listener : FlowerJuiceListener,
) : RecyclerView.Adapter<FlowerJuiceListAdapter.FlowerJuiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerJuiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return FlowerJuiceViewHolder(view)
    }

    interface FlowerJuiceListener{
        fun onFlowerJuiceClick(flowerJuice:Product, position:Int)
        fun onProductAddClick(flowerJuice: Product, position :Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FlowerJuiceViewHolder, position: Int) {

        val juice = juiceList[position]
        val name  = juice.name
        val price = juice.price
        val image = juice.image


        holder.binding.apply {

            tvProductName.text = name
            tvProductPrice.text= "Price: $price TK"
            ivProductImage.let{
                Glide.with(context)
                    .load(image)
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.mipmap.ic_app_logo)
                    .into(it)
            }

            itemProduct.setOnClickListener {
                listener.onFlowerJuiceClick(juice, position)
            }
            btnAddToCart.setOnClickListener {
                listener.onProductAddClick(juice, position)
            }

        }
    }

    override fun getItemCount(): Int {
        return juiceList.size
    }

    inner class FlowerJuiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemProductBinding.bind(itemView)
    }

}