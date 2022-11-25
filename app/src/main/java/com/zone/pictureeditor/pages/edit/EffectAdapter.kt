package com.zone.pictureeditor.pages.edit

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zone.pictureeditor.R

// 滤镜的adapter
class EffectAdapter(
    private val myContext: Context,
    private val curUri: String // current uri
): RecyclerView.Adapter<EffectAdapter.EffectsViewHolder>() {
    inner class EffectsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var surfaceView = itemView.findViewById(R.id.img_effect_preview) as GLSurfaceView
        var effectName = itemView.findViewById(R.id.effect_name) as TextView
    }

    private var myEffectList: ArrayList<String>? = null
    private var myInflater: LayoutInflater = LayoutInflater.from(myContext)
    lateinit var view: View
    private var type: Int = 0

    init {
        this.myEffectList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EffectsViewHolder {
        view = myInflater.inflate(R.layout.effect_list_card, parent, false)
        return EffectsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EffectsViewHolder, position: Int) {
        val curEffect = myEffectList!![position]
        holder.effectName.text = curEffect
        holder.surfaceView.setEGLContextClientVersion(2)
        var er = EffectsRenderer(myContext, curUri)

        holder.surfaceView.setRenderer(er)
        holder.surfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        er.setCurEffect(position+(type*10))
        er.setFactor(0.5f)
        holder.surfaceView.requestRender()
        holder.itemView.setOnClickListener {
            (myContext as EditActivity).onEffectClicked(position+(type*10))
//         TODO 保留这一句?
        //          navigationView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return myEffectList?.size ?: 0
    }

    fun setEffectList(effectList: List<String>, curType: Int) {
        this.myEffectList!!.clear()
        this.myEffectList!!.addAll(effectList)
        this.type = curType
        notifyDataSetChanged() // 通知数据修改了, 要不然会崩溃
    }
}