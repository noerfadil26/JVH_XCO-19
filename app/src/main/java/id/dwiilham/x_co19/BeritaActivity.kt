package id.dwiilham.x_co19

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


class BeritaActivity : AppCompatActivity() {

    private lateinit var txtTitle: TextView
    private lateinit var txtImage: ImageView
    private lateinit var txtTime: TextView
    private lateinit var tetArticle: WebView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita)

        txtTitle = findViewById(R.id.txt_judul)
        txtImage = findViewById(R.id.txt_image)
        txtTime = findViewById(R.id.txt_time)
        tetArticle = findViewById(R.id.webview)
        btnBack = findViewById(R.id.btn_back)

        val intent = getIntent()

        txtTitle.text = intent.getStringExtra("title")
        txtTime.text = intent.getStringExtra("time")
        Picasso.get().load(intent.getStringExtra("image")).into(txtImage)
        val unencodedHTML = "<html><body>" + intent.getStringExtra("artikel") + "</body></html>"
        val encodedHTML = Base64.encodeToString(unencodedHTML.toByteArray(), Base64.NO_PADDING)
        Log.d("HTML", "LOADING")
        //txtArtikel.loadData(encodedHTML, "text/html", null)

        val unencodedHtml = "<html><body>'%28' is the code for '('</body></html>"
        val encodedHtml =
            Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING).toString()
        tetArticle.loadData(encodedHTML, "text/html; charset=utf-8", "base64")


        btnBack.setOnClickListener {
            finish()
        }

    }

}
