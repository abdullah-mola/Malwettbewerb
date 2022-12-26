package abdullah.mola.malwettbewerb.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.util.*


data class SubmissionImage(
    val id: Int,
    val name: String,
    val extension: String,
    val size: Long,
    val location: String,
    @SerializedName("public_location")
    val publicLocation: String,
    @SerializedName("imageable_type")
    val imageableType: String,
    @SerializedName("imageable_id")
    val imageableId: UUID,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)