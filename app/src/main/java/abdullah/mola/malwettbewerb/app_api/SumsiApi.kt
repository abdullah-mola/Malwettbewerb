package abdullah.mola.malwettbewerb.app_api

import android.util.Log
import abdullah.mola.malwettbewerb.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

interface SumsiApi {
    fun getSubmissions(submission: (SubmissionResponse) -> Unit)
    fun postSubmissions(body: RequestBody, submission: (PostSubmissionsResult) -> Unit)
    fun postVote(
        vote: PostVotings,
        submissionId: String,
        lambdaOnComplete: (PostVotingsResponse) -> Unit,
        lambdaOnError: () -> Unit
    )
}

class SumsiApiImpl : SumsiApi {
    val TAG: String = "SumsiApi"

    override fun getSubmissions(getSubmission: (SubmissionResponse) -> Unit) {
        NetworkManager.sumsiBackend.getPosts().enqueue(object : Callback<SubmissionResponse> {
            override fun onResponse(
                call: Call<SubmissionResponse>,
               response: Response<SubmissionResponse>
            ) {
                response.body()?.let {
                    response
                    getSubmission.invoke(it)
                }

            }

            override fun onFailure(call: Call<SubmissionResponse>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)

            }

            })
    }

    override fun postSubmissions(
        body: RequestBody,
        getSubmissionPost: (PostSubmissionsResult) -> Unit
    ) {
        NetworkManager.sumsiBackend.postsubmission(body)
            .enqueue(object : Callback<PostSubmissionsResult> {
                override fun onResponse(
                    call: Call<PostSubmissionsResult>,
                    response: Response<PostSubmissionsResult>
                ) {
                    response.body()?.let {
                        getSubmissionPost.invoke(it)
                    }
                }

                override fun onFailure(call: Call<PostSubmissionsResult>, t: Throwable) {
                    Log.d("Post Submission", t.localizedMessage)
                }

            })
    }

    override fun postVote(
        vote: PostVotings,
        submissionId: String,
        lambdaOnComplete: (PostVotingsResponse) -> Unit,
        lamdaOnError: () -> Unit
    ) {
        NetworkManager.sumsiBackend.postVote(vote, submissionId).enqueue(
            object : Callback<PostVotingsResponse> {
                override fun onResponse(
                    call: Call<PostVotingsResponse>,
                    response: Response<PostVotingsResponse>
                ) {
                    response?.code()?.let {
                        if (it != 200)
                            lamdaOnError.invoke()
                    }
                    response?.body()?.let {
                        lambdaOnComplete.invoke(it)
                    }
                }

                override fun onFailure(call: Call<PostVotingsResponse>, t: Throwable) {
                    Log.d("Post Voting", t.localizedMessage)
                }
            }
        )
    }

}