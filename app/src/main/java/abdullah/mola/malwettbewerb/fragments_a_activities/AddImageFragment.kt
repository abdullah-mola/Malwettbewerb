package abdullah.mola.malwettbewerb.fragments_a_activities


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import abdullah.mola.malwettbewerb.R
import abdullah.mola.malwettbewerb.app_api.SumsiApi
import abdullah.mola.malwettbewerb.app_api.SumsiApiImpl
import abdullah.mola.malwettbewerb.app_api.SumsiBackend
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource

class AddImageFragment : Fragment() {
    private lateinit var button: Button
    private lateinit var buttonGallary: Button
    private lateinit var save: Button
    private lateinit var cancel: Button
    private lateinit var imageview: ImageView
    private lateinit var flegel: TextInputLayout
    private lateinit var llegel: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var chfirst: TextInputLayout
    private lateinit var chlast: TextInputLayout
    private lateinit var chAge: TextInputLayout
    private lateinit var privacy: SwitchMaterial
    private lateinit var participation: SwitchMaterial
    private lateinit var mail: SwitchMaterial
    var image: File? = null
    lateinit var easyImage: EasyImage

    //    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        easyImage = EasyImage.Builder(requireContext()).allowMultiple(false).build()
        buttonGallary.setOnClickListener {
            chooseImageGallary()
        }
        cancel.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_addImageFragment_to_showAllImagesFragment)
        }
        button.setOnClickListener {
            chooseImage()
        }


        save.setOnClickListener {

            val email = email.editText?.text.toString()
            val body = MultipartBody.Builder().apply {
                setType(MultipartBody.FORM)
                addFormDataPart("legalguardian_firstname", flegel.editText?.text.toString())
                addFormDataPart("legalguardian_lastname", llegel.editText?.text.toString())
                addFormDataPart("email", email)
                addFormDataPart("child_firstname", chfirst.editText?.text.toString())
                addFormDataPart("child_age", chAge.editText?.text.toString())
                addFormDataPart("approval_privacypolicy", if (privacy.isChecked) "1" else "0")
                addFormDataPart(
                    "approval_participation",
                    if (participation.isChecked) "1" else "0"
                )
                addFormDataPart("approval_mailnotification", if (mail.isChecked) "1" else "0")
                val imageByte = image?.readBytes()
                if (imageByte != null)
                    addFormDataPart(
                        "image",
                        "submission.jpg",
                        imageByte.toRequestBody("image/jpg".toMediaType())
                    )
            }.build()
            val sumsiApi: SumsiApi = SumsiApiImpl()
            sumsiApi.postSubmissions(body) {
                when (it.statusCode) {
                    422 -> {
                        when (it.message) {
                            "You can only add one Photo per Mail Address" -> Toast.makeText(
                                context,
                                "You can only add one Photo per Mail Address",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    200 -> {
                        Toast.makeText(context, "Picture uploaded Successfully", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view)
                            .navigate(R.id.action_addImageFragment_to_showAllImagesFragment)
                    }
                    else -> {
                        Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }


        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_image, container, false)
        buttonGallary = view.findViewById(R.id.buttonLoadGallary)
        button = view.findViewById(R.id.buttonLoadPicture)
        save = view.findViewById(R.id.Save)
        cancel = view.findViewById(R.id.cancel)
        imageview = view.findViewById(R.id.imageView)
        flegel = view.findViewById(R.id.flegel)
        llegel = view.findViewById(R.id.llegel)
        email = view.findViewById(R.id.email)
        chfirst = view.findViewById(R.id.chfirst)
        chlast = view.findViewById(R.id.chlast)
        chAge = view.findViewById(R.id.chAge)
        privacy = view.findViewById(R.id.privacy)
        participation = view.findViewById(R.id.participation)
        mail = view.findViewById(R.id.mail)
        return view
    }

    private fun chooseImage() {
        Log.d("MainActivity", "open Gallery ${easyImage == null}")
        easyImage.openCameraForImage(this)
    }

    private fun chooseImageGallary() {
        Log.d("MainActivity", "open Gallery ${easyImage == null}")
        easyImage.openGallery(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    val myBitmap = BitmapFactory.decodeFile(
                        imageFiles[0].file.path
                    )
                    imageview.setImageBitmap(myBitmap)
                    imageFiles.forEach {
                        Log.d("Images", it.file.path)
                        it.file.readBytes()
                    }
                    image = imageFiles[0].file
                }
            })
    }
    override fun onStart() {
        super.onStart()

        this.setTitle(getString(R.string.app_name))
    }
}




