package abdullah.mola.malwettbewerb.model

import com.google.gson.annotations.SerializedName

data class PostSubmissionsResult(
    val status: String,
    @SerializedName("status_code")
    val statusCode: Int,
    val message: String
)