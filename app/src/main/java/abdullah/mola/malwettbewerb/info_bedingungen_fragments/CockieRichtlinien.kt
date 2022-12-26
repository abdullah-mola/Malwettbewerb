package abdullah.mola.malwettbewerb.info_bedingungen_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.fragments_a_activities.setTitle

class CockieRichtlinien : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cockie_richtlinien, container, false)
        val webView: WebView = view.findViewById(R.id.webViewCockieRechtLinien)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.raiffeisen-sumsi.at/cookies/")


        return view
    }
    override fun onStart() {
        super.onStart()

        this.setTitle(getString(R.string.app_name))
    }
}