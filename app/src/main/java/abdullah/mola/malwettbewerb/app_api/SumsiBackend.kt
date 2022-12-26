package abdullah.mola.malwettbewerb.app_api
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SumsiBackend {

    @POST("login")
    fun login(@Body body: LoginRequestBody): Call<LoginResponse>

    @GET("submissions")
    fun getPosts(): Call<SubmissionResponse>



    @Headers("Accept: application/json")
    @POST("submissions")
    fun postsubmission(@Body body: RequestBody): Call<PostSubmissionsResult>

    @POST("submissions/{submission}/votings")
    fun postVote(
        @Body vote: PostVotings,
        @Path("submission") submissionId: String
    ): Call<PostVotingsResponse>
}