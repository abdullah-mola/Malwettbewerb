package abdullah.mola.malwettbewerb.fragments_a_activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.app_api.SumsiApi
import abdullah.mola.malwettbewerb.app_api.SumsiApiImpl
import abdullah.mola.malwettbewerb.viewadapters.ShowAllRCAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Fragment.setTitle(title: String) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = title
}

class ShowAllImagesFragment : Fragment() {

    private lateinit var gridLayoutManager: GridLayoutManager
    var title = "Sumsi"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_all_images, container, false)
        val sumsiApi: SumsiApi = SumsiApiImpl()
        //Button Navigation to Add image Fragment
        val btnToAddFragment: FloatingActionButton = view.findViewById(R.id.fbShowAllToAdd)
        btnToAddFragment.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_showAllImagesFragment_to_addImageFragment)
        }
        //RecyclerView implementation
        val rvAdapter = ShowAllRCAdapter(onItemClicked = {
            Navigation.findNavController(view).navigate(
                ShowAllImagesFragmentDirections.navigateShowAllToVoteImage(
                    Gson().toJson(it)
                )
            )
        })
        val rvShowAll: RecyclerView = view.findViewById(R.id.rvShowAll)

        rvShowAll.adapter = rvAdapter
        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        rvShowAll.layoutManager = gridLayoutManager
        rvShowAll.setHasFixedSize(true)

        GlobalScope.launch {
            delay(1000)
            sumsiApi.getSubmissions {
                rvAdapter.setListData(
                    it.data
                )
                rvAdapter.notifyDataSetChanged()
            }
        }


        return view
    }

    override fun onStart() {
        super.onStart()

        this.setTitle(getString(R.string.app_name))
    }


}