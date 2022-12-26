package abdullah.mola.malwettbewerb.fragments_a_activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.app_api.SumsiApi
import abdullah.mola.malwettbewerb.app_api.SumsiApiImpl
import abdullah.mola.malwettbewerb.model.PostVotings
import abdullah.mola.malwettbewerb.model.Submission
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class VoteImageFragment : Fragment() {

    private lateinit var imageBtn: ImageButton
    private val args: VoteImageFragmentArgs by navArgs<VoteImageFragmentArgs>()
    private lateinit var imgToVote: ImageView
    private lateinit var voteIcon: TextView
    private lateinit var etMail:TextInputLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submissionString = args.submission
        val submission = Gson().fromJson(submissionString, Submission::class.java)
        val sumsiApi: SumsiApi = SumsiApiImpl()
        var votes = submission.votings.size
        etMail = view.findViewById(R.id.lyMail)
        imageBtn.setOnClickListener {
            val mail: String = etMail.editText?.text.toString()

            sumsiApi.postVote(
                PostVotings(email = mail),
                submissionId = submission.id,
                lambdaOnComplete = {
                    when (it.statusCode) {
                        500 -> {
                            when (it.message) {
                                "Only 5 votes per user allowed." -> Toast.makeText(
                                    context,
                                    "Only 5 votes per email allowed",
                                    Toast.LENGTH_SHORT
                                ).show()
                                "Only 1 vote per image allowed." -> Toast.makeText(
                                    context,
                                    "Only one vote per Image allowed",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                        200 -> {
                            votes++
                            voteIcon.text = "Likes $votes"
                        }
                        else -> {
                            Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                lambdaOnError = {

                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT)
                        .show()

                }
            )
        }
        voteIcon.text = votes.toString()
        submission.image?.publicLocation?.let {
            Picasso.get()
                .load("https://sumsi.dev.webundsoehne.com${it}")
                .into(imgToVote)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vote_image, container, false).apply {
            imageBtn = findViewById(R.id.imgBtn)
            imgToVote = findViewById(R.id.imgToVote)
            voteIcon = findViewById(R.id.voteIcon)

        }

    }
    override fun onStart() {
        super.onStart()

        this.setTitle(getString(R.string.app_name))
    }
}