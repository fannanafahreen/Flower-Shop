package fannana.fahreen.floresdejardin.ui.flower_list

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

class FlowerListAdapter(
    private val flowerList: MutableList<Product>,
    private val context : Context,
    private val listener : FlowerListener,
) : RecyclerView.Adapter<FlowerListAdapter.FlowerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent,false)
        return FlowerViewHolder(view)
    }

    interface FlowerListener{
        fun onFlowerClick(flower: Product, position :Int)
        fun onProductAddClick(flower: Product, position :Int)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {

        val flower = flowerList[position]
        val name  = flower.name
        val price = flower.price
        val image = flower.image


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
                listener.onFlowerClick(flower, position)
            }
            btnAddToCart.setOnClickListener {
                listener.onProductAddClick(flower, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

    inner class FlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemProductBinding.bind(itemView)
    }

}