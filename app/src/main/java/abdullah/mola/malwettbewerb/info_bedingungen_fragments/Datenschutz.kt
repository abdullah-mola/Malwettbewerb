package abdullah.mola.malwettbewerb.info_bedingungen_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.fragments_a_activities.setTitle
import com.google.android.material.navigation.NavigationView

class Datenschutz : Fragment() {

    lateinit var inflater: LayoutInflater
    lateinit var container: ViewGroup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_datenschutz, container, false)
        val webView: WebView = view.findViewById(R.id.webViewDatenSchutz)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.raiffeisen-sumsi.at/gewinnspielrichtlinien-lt-dsgvo/")

        return view
    }

    override fun onStart() {
        super.onStart()

        this.setTitle(getString(R.string.app_name))
    }
}

