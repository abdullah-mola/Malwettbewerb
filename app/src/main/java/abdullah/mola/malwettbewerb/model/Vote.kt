package abdullah.mola.malwettbewerb.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Vote(
    val id: UUID,
    val email: String,
    @SerializedName("submission_id")
    val submissionId: UUID,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
