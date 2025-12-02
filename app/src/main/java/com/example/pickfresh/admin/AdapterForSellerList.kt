package com.example.pickfresh.admin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pickfresh.databinding.ActivitySellerMainBinding
import com.example.pickfresh.responses.RentalResponse


class AdapterForSellerList(val context: Context, val data: ArrayList<RentalResponse.Rentals>) :
    RecyclerView.Adapter<AdapterForSellerList.Viewed>() {
    class Viewed(val item: ActivitySellerMainBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Viewed(ActivitySellerMainBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: Viewed, position: Int) {
        val k = data[position]
        with(holder.item) {
            val date = k.dateOn?.toLongOrNull()
            val string =
                "<b>Name : </b>${k.rentalType}<br>" + "<b>Description : </b>${k.rentalDes}<br>" + "<b>Cost Per Hour : </b>₹${k.rentalRupees}/-<br>" + "<b>Added in:</b>${date}"
            details.text = HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY)
            shapeimage.load(k.imageurl)
            telPoint.setOnClickListener { _ ->
                context.startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:${k.rentalMobile}")
                    )
                )
            }
        }

    }

    override fun getItemCount() = data.size

}
