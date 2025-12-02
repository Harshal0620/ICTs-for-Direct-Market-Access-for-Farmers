package com.example.pickfresh.seller

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pickfresh.buyer.LoadUrl
import com.example.pickfresh.databinding.ViewCardBinding
import com.example.pickfresh.responses.ResponseOfSchemes

class AdapterForScheme(
    val context: Context, val array: ArrayList<ResponseOfSchemes.SchemesView>,
) : RecyclerView.Adapter<AdapterForScheme.ViewPoint>() {
    class ViewPoint(val view: ViewCardBinding) : RecyclerView.ViewHolder(view.root)

    override fun getItemCount() = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewPoint(
        ViewCardBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewPoint, position: Int) {
        array[position].let {
            with(holder.view) {
                detailsPoint.text = HtmlCompat.fromHtml(
                    it.schemeDes ?: "", HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
                )
                linkTab.setOnClickListener { _ ->
                    context.startActivity(
                        Intent(context, LoadUrl::class.java).putExtra(
                            "urlPoint", it.url
                        )
                    )

                }
            }
        }
    }
}