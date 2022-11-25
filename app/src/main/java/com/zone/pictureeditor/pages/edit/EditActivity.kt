package com.zone.pictureeditor.pages.edit

import android.app.Activity
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zone.pictureeditor.R

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
    private var curPos : Int = 0
    var mFactor : Float = 0.0f

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.effect_filter -> {
                navigationView.menu.getItem(1).isChecked = true
//                effect_transform_bar.visibility = View.GONE
                myRecyclerView.adapter = myAdapter
                myAdapter.setEffectList(EffectNames.filters,0)
            }
            R.id.effect_enhance -> {
                navigationView.menu.getItem(2).isChecked = true
//                effect_transform_bar.visibility = View.GONE
                myRecyclerView.adapter = myAdapter
                myAdapter.setEffectList(EffectNames.enhance,1)
            }
            R.id.effect_transform -> {
                navigationView.menu.getItem(0).isChecked = true

            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        surfaceView = findViewById(R.id.photo_for_edit)
        surfaceView.setEGLContextClientVersion(2)
        navigationView = findViewById(R.id.navigationEffect)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        effectDoneBtn = findViewById(R.id.effect_done_btn)
        seekBar = findViewById(R.id.seek_bar_effect)
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        effectDoneBtn.setOnClickListener{
            navigationView.visibility = View.VISIBLE
        }
    }

    var seekBarChangeListener: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            // updated continuously as the user slides the thumb
            er.setCurEffect(curPos)
//            Log.v("EffectPos",curPos.toString())
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

    }
}