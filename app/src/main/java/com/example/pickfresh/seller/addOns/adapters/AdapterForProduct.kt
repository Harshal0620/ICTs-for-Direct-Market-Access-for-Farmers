package com.example.pickfresh.seller.addOns.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pickfresh.databinding.ActivitySellerMainBinding
import com.example.pickfresh.responses.FertiProducts

class AdapterForProduct(
    val context: Context,
    val data: ArrayList<FertiProducts.Data>,
    val click: (FertiProducts.Data) -> Unit,
) :
    RecyclerView.Adapter<AdapterForProduct.Viewed>() {
    class Viewed(val item: ActivitySellerMainBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Viewed(ActivitySellerMainBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: Viewed, position: Int) {
        val k = data[position]
        with(holder.item) {
            val string = "<b>Name : </b>${k.name}<br>" + "<b>Cost : </b>${k.cost}"
            details.text = HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY)
            shapeimage.load(k.image)
            telPoint.isVisible = false
            telPoint.setOnClickListener { _ ->
                click.invoke(k)
            }
            buy.isVisible=true
            buy.setOnClickListener {
                click.invoke(k)
            }
        }

    }

    override fun getItemCount() = data.size
}
