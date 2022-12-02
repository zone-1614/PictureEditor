package com.zone.pictureeditor.pages.edit

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaMetadataRetriever
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.core.graphics.createBitmap
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.zone.pictureeditor.MainActivity
import com.zone.pictureeditor.R
import com.zone.pictureeditor.util.toast
import java.util.*

class EditActivity : Activity() {

    // 滤镜的view以及adapter
    lateinit var myRecyclerView: RecyclerView
    private lateinit var myAdapter: EffectAdapter

    lateinit var surfaceView : GLSurfaceView
    lateinit var curUri : String
    lateinit var er : EffectsRenderer
    lateinit var navigationView: BottomNavigationView
    lateinit var effectDoneBtn : ImageButton
    lateinit var seekBar : SeekBar
    lateinit var effect_rotate: ImageButton
    lateinit var effect_crop: ImageButton
    private var curPos : Int = 0
    var mFactor : Float = 0.0f

    lateinit var effect_transform_bar: View

    lateinit var back_button: ImageButton
    lateinit var save_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        surfaceView = findViewById(R.id.photo_for_edit)
        surfaceView.setEGLContextClientVersion(2)
        navigationView = findViewById(R.id.navigationEffect)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        navigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        effectDoneBtn = findViewById(R.id.effect_done_btn)
        seekBar = findViewById(R.id.seek_bar_effect)
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        effect_transform_bar = findViewById(R.id.effect_transform_bar)
        effectDoneBtn.setOnClickListener {
            navigationView.visibility = View.VISIBLE
        }

        curUri = intent.getStringExtra("curPic")!!
        er = EffectsRenderer(this, curUri)
        surfaceView.setRenderer(er)
        surfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        myRecyclerView = findViewById(R.id.effects_recycler_view)
        myRecyclerView.addItemDecoration(SpacesItemDecoration(4))
        myRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        myAdapter = EffectAdapter(this, curUri, navigationView)
        myRecyclerView.adapter = myAdapter
        myRecyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
//        myAdapter.setEffectList(EffectNames.filters, 0)

        effect_rotate = findViewById(R.id.effect_rotate)
        effect_rotate.setOnClickListener {
            onEffectClicked(20)
        }

        effect_crop = findViewById(R.id.effect_crop)
        effect_crop.setOnClickListener {
            "click crop".toast()
        }

        back_button = findViewById(R.id.back_button)
        back_button.setOnClickListener {
            finish()
        }

        save_button = findViewById(R.id.save_button)
        save_button.setOnClickListener {
            val bitmap = Bitmap.createBitmap(surfaceView.width, surfaceView.height, Bitmap.Config.ARGB_8888)
            val listener = PixelCopy.OnPixelCopyFinishedListener { copyResult: Int ->
                saveBitmap(bitmap)
            }
            PixelCopy.request(surfaceView, bitmap, listener, Handler(Looper.getMainLooper()))
        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.effect_filter -> {
                if (navigationView.menu.getItem(1).isChecked)
                    return@OnNavigationItemSelectedListener false
                navigationView.menu.getItem(1).isChecked = true
                effect_transform_bar.visibility = View.GONE
                myRecyclerView.adapter = myAdapter
                myAdapter.setEffectList(EffectNames.filters, 0)
            }
            R.id.effect_enhance -> {
                if (navigationView.menu.getItem(2).isChecked)
                    return@OnNavigationItemSelectedListener false
                navigationView.menu.getItem(2).isChecked = true
                effect_transform_bar.visibility = View.GONE
                myRecyclerView.adapter = myAdapter
                myAdapter.setEffectList(EffectNames.enhance, 1)
            }
            R.id.effect_transform -> {
                if (navigationView.menu.getItem(0).isChecked)
                    return@OnNavigationItemSelectedListener false
                navigationView.menu.getItem(0).isChecked = true
                effect_transform_bar.visibility = View.VISIBLE
            }
        }
        false
    }

    var seekBarChangeListener: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            // updated continuously as the user slides the thumb
            er.setCurEffect(curPos)
            mFactor = progress / (100.0f)
            er.setFactor(mFactor)
            surfaceView.requestRender()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // called when the user first touches the SeekBar
            er.setCurEffect(curPos)
            er.setFactor(mFactor)
            surfaceView.requestRender()
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // called after the user finishes moving the SeekBar
            er.setCurEffect(curPos)
            er.setFactor(mFactor)
            surfaceView.requestRender()
        }
    }

    fun onEffectClicked(pos: Int) {
        curPos = pos
        er.setCurEffect(pos)
        surfaceView.requestRender()
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val calendar = Calendar.getInstance()
        val filename = "/PE_${calendar.get(Calendar.YEAR)}_${calendar.get(Calendar.MONTH) + 1}" +
                "_${calendar.get(Calendar.DAY_OF_MONTH)}_${calendar.get(Calendar.HOUR_OF_DAY)}" +
                "_${calendar.get(Calendar.MINUTE)}_${calendar.get(Calendar.SECOND)}.png"
        MediaStore.Images.Media.insertImage(contentResolver, bitmap, filename, "image from picture editor")
    }
}