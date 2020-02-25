package com.example.scopedstorage.accessmedialocation

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.Glide
import com.example.scopedstorage.R
import com.example.scopedstorage.const.CheckPermission.checkPermissionForAML
import com.example.scopedstorage.const.CheckPermission.requestPermissionForAML
import com.example.scopedstorage.const.Constants.CHOOSE_FILE
import kotlinx.android.synthetic.main.activity_access_media_location.*
import java.io.IOException
import java.io.InputStream


class AccessMediaLocationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_media_location)
    }

    fun chooseFile(view: View) {
        Log.i("Tag", "chooseFile")

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {

            openIntentChooser()

        } else {

            if (checkPermissionForAML(this)) {

                openIntentChooser()
            } else {
                Log.i("Tag", "else chooseFile")

                requestPermissionForAML(this)
            }


        }


    }

    fun openIntentChooser() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_FILE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CHOOSE_FILE) {
                if (data != null) {


                    var inputStream: InputStream? = null
                    try {
                        inputStream = contentResolver.openInputStream(data.data!!)
                        val exifInterface = ExifInterface(inputStream!!)



                        //ExifInterface.TAG_IMAGE_LENGTH
                        //ExifInterface.TAG_IMAGE_WIDTH
                        //ExifInterface.TAG_DATETIME
                        //ExifInterface.TAG_MAKE
                        //ExifInterface.TAG_MODEL
                        //ExifInterface.TAG_ORIENTATION
                        //ExifInterface.TAG_WHITE_BALANCE
                        //ExifInterface.TAG_FOCAL_LENGTH
                        //ExifInterface.TAG_FLASH
                        //ExifInterface.TAG_GPS_DATESTAMP
                        //ExifInterface.TAG_GPS_TIMESTAMP
                        //ExifInterface.TAG_GPS_LATITUDE
                        //ExifInterface.TAG_GPS_LATITUDE_REF
                        //ExifInterface.TAG_GPS_LONGITUDE
                        //ExifInterface.TAG_GPS_LONGITUDE_REF
                        //ExifInterface.TAG_GPS_PROCESSING_METHOD

                        Log.i(
                            "TAG",
                            "===${exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)}"
                        )
                        Glide.with(ivImage)
                            .load(data.dataString)
                            .thumbnail(0.33f)
                            .into(ivImage)

                        tvImagePath.text =
                            String.format(getString(R.string.path_for_image), data.data)
                        tvLocationLat.text = String.format(
                            getString(R.string.lat_for_image),
                            exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                        )
                        tvLocationLag.text = String.format(
                            getString(R.string.lat_for_image),
                            exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
                        )
                        // Now you can extract any Exif tag you want
                        // Assuming the image is a JPEG or supported raw format
                    } catch (e: IOException) {
                        // Handle any errors
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close()
                            } catch (ignored: IOException) {
                            }

                        }
                    }

                }
            }
        }

    }


}