package abdullah.mola.malwettbewerb.model

import com.google.gson.annotations.SerializedName

data class Submission(
    val id: String,
    @SerializedName("legalguardian_firstname")
    val legalguardianFirstname: String,
    @SerializedName("legalguardian_lastname")
    val legalguardianLastname: String,
    val email: String,
    @SerializedName("child_firstname")
    val childFirstname: String,
    @SerializedName("child_age")
    val childAge: String,
    @SerializedName("approval_privacypolicy")
    val approvalPrivacypolicy: Int,
    @SerializedName("approval_participation")
    val approvalParticipation: Int,
    @SerializedName("approval_mailnotification")
    val approvalMailnotification: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val votings: List<Vote>,
    val image: SubmissionImage?
)