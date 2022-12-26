package abdullah.mola.malwettbewerb.info_bedingungen_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.fragments_a_activities.setTitle

class AGB : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a_g_b, container, false)
        val webView: WebView = view.findViewById(R.id.webViewAGB)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.raiffeisen.at/ktn/rlb/de/meine-bank/raiffeisen-bankengruppe/agb.html")
        return view
    }

    override fun onStart() {
        super.onStart()

        this.setTitle(getString(R.string.app_name))
    }
}