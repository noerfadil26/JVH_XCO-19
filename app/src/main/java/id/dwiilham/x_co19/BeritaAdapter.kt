package id.dwiilham.x_co19

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.dwiilham.x_co19.callback.BeritaCallback

class BeritaAdapter(private val context: Context, private val body: List<BeritaCallback>?) : RecyclerView.Adapter<BeritaAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
        var title = view.findViewById<TextView>(R.id.artikel_text)
        var time = view.findViewById<TextView>(R.id.artikel_time)
        var layoutSpoiler = view.findViewById<LinearLayout>(R.id.layout_spoiler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_spoiler, parent, false) as View

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = body?.size!!

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(body?.get(position)?.image).into(holder.thumbnail)
        holder.time.text = body?.get(position)?.time
        holder.title.text = body?.get(position)?.title

        holder.layoutSpoiler.setOnClickListener {
            val intent = Intent(context, BeritaActivity::class.java)
                intent.putExtra("title", body?.get(position)?.title)
                intent.putExtra("time", body?.get(position)?.time)
                intent.putExtra("image", body?.get(position)?.image)
                intent.putExtra("artikel", body?.get(position)?.artikel)
            it.context.startActivity(intent)
        }
    }

}
