package abdullah.mola.malwettbewerb.model

import com.google.gson.annotations.SerializedName

data class PostVotings(
    val email: String
)

data class PostVotingsResponse(
    val status: String,
    @SerializedName("status_code")
    val statusCode: Int,
    val message: String,
    val data: List<Submission>?
)