package com.example.pickfresh.seller.addOns.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pickfresh.databinding.ActivitySellerMainBinding
import com.example.pickfresh.responses.UserResponsesForIt
import java.text.SimpleDateFormat
import java.util.Date

class AdapterForUserOrder(
    val context: Context,
    val data: ArrayList<UserResponsesForIt.Data>,
    val click: (UserResponsesForIt.Data) -> Unit,
) :
    RecyclerView.Adapter<AdapterForUserOrder.Viewed>() {
    class Viewed(val item: ActivitySellerMainBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Viewed(ActivitySellerMainBinding.inflate(LayoutInflater.from(context), parent, false))

    @SuppressLint("SimpleDateFormat")
    val simpleDate = SimpleDateFormat("dd-M-yyyy hh:mm:s")
    override fun onBindViewHolder(holder: Viewed, position: Int) {
        val k = data[position]
        with(holder.item) {
            val date = k.dateOn?.toLongOrNull()?.let {
                simpleDate.format(Date(it))
            }
            val string =
                "<b>User name : </b>${k.name}<br>" + "<b>Cost : </b>${k.cost}" + "<br><b>Quantity : </b>${k.qty}" +
                        "<br><b>Placed in :</b>${date}" +
                        "<br><b>Order Status :</b>${k.status}"
            details.text = HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
            shapeimage.load(k.image)
            telPoint.isVisible = true
            telPoint.setOnClickListener { _ ->
                context.startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:${k.mobile}")
                    )
                )
            }
            if (k.status == "Pending") {
                statusPoint.text = "Update"
                statusPoint.setTextColor(Color.BLACK)
                buy.isVisible = true
                buy.setOnClickListener {
                    click.invoke(k)
                }
            }

        }

    }

    override fun getItemCount() = data.size
}
