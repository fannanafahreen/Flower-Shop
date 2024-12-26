package fannana.fahreen.floresdejardin.ui.plant_list

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


class PlantListAdapter (
        private val plantList: MutableList<Product>,
        private val context : Context,
        private val listener : PlantListener,
):RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup,viewType:Int):PlantViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent,false)
                return PlantViewHolder(view)
        }

        interface PlantListener{
                fun onPlantClick(plant: Product,position:Int)
                fun onProductAddClick(flower: Product, position :Int)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {

                val plant = plantList[position]

                val name  =plant.name
                val price =plant.price
                val image =plant.image


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
                                listener.onPlantClick(plant, position)
                        }
                        btnAddToCart.setOnClickListener {
                                listener.onProductAddClick(plant, position)
                        }
                }
        }

        override fun getItemCount(): Int {
                return plantList.size
        }


        inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val binding = ItemProductBinding.bind(itemView)
        }

}

