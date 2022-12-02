package com.zone.pictureeditor.pages.edit

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import android.net.Uri
import android.opengl.GLES11Ext
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.provider.MediaStore
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class EffectsRenderer(): GLSurfaceView.Renderer {
    companion object {
        var angle: Int = 0
    }

    private var photo: Bitmap? = null
    private var photoWidth: Int = 0
    private var photoHeight: Int = 0
    private var effectContext: EffectContext? = null
    private var effect: Effect? = null
//    var bitmap: Bitmap? = null
    private var mFactor: Float = 0.0f

    private var mIndex: Int? = null

    fun setCurEffect(pos: Int) {
        mIndex = pos
    }

    fun setFactor(factor: Float) {
        mFactor = factor
    }

    constructor(context: Context, curUri: String) : this() {
        photo = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(curUri))
        photoWidth = photo!!.width
        photoHeight = photo!!.height
//        Log.e("ZONE_DEBUG", "constructor of renderer")
        mIndex = 0
    }

    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0

    private val textures = IntArray(2)
    private var square: Square? = null

    /**
     * 画出裁剪框 (crop)
     */
    private fun generateSquare(arr : FloatArray) {
        GLES20.glGenTextures(2, textures, 0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])
//        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0])
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, photo, 0)
        square = Square(arr)
        Log.e("ZONE_DEBUG", "generateSquare")
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.e("ZONE_DEBUG", "onSurfaceCreated")
        var coords = computeOutputVertices()
        generateSquare(coords)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.e("ZONE_DEBUG", "onSurfaceChanged")
        mViewHeight = height
        mViewWidth = width
        photo = getResizedBitmap(photo!!, mViewWidth, mViewHeight)
        photoWidth = photo!!.width
        photoHeight = photo!!.height
        GLES20.glViewport(0, 0, width, height)
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        var coords = computeOutputVertices()
        generateSquare(coords)
    }

    override fun onDrawFrame(gl: GL10?) {
        if (effectContext == null) {
            effectContext = EffectContext.createWithCurrentGlContext()
        }
        if (effect != null) {
            effect!!.release()
        }
        GLES20.glViewport(0, 0, mViewWidth, mViewHeight)
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        when (mIndex) {
            3 -> documentaryEffect()
            1 -> blackAndWhiteEffect(mFactor)
            2 -> duotoneEffect()
            0 -> grainEffect(mFactor)
            4 -> grayScaleEffect()
            5 -> negativeEffect()
            6 -> posterizeEffect()
            7 -> sepiaEffect()
            8 -> vignetteEffect(mFactor)

            10 -> autofixEffect(mFactor)
            11 -> brightnessEffect(mFactor)
            12 -> contrastEffect(mFactor)
            13 -> crossprocessEffect(mFactor)
            14 -> fisheyeEffect(mFactor)
            15 -> sharpenEffect(mFactor)
            16 -> straightenEffect(mFactor)
            17 -> temperatureEffect(mFactor)
            18 -> tintEffect(mFactor)

            20 -> rotateEffect(mFactor)

            else -> autofixEffect(mFactor)
        }
        square!!.draw(textures[1])
    }

    private fun blackAndWhiteEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_BLACKWHITE)
        effect!!.setParameter("black", factor)
        effect!!.setParameter("white", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun documentaryEffect() {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_DOCUMENTARY)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun duotoneEffect() {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_DUOTONE)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun grainEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_GRAIN)
        effect!!.setParameter("strength", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun grayScaleEffect() {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_GRAYSCALE)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun negativeEffect() {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_NEGATIVE)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun posterizeEffect() {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_POSTERIZE)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun sepiaEffect() {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_SEPIA)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun vignetteEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_VIGNETTE)
        effect!!.setParameter("scale", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun autofixEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_AUTOFIX)
        effect!!.setParameter("scale", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun brightnessEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_BRIGHTNESS)
        effect!!.setParameter("brightness", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun contrastEffect(factor : Float = 1.0f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_CONTRAST)
        effect!!.setParameter("contrast", factor*10)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun crossprocessEffect(factor : Float = 1.0f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_CROSSPROCESS)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun fisheyeEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_FISHEYE)
        effect!!.setParameter("scale", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun sharpenEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_SHARPEN)
        effect!!.setParameter("scale", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun straightenEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_STRAIGHTEN)
        effect!!.setParameter("angle", (factor-0.5f)*90)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun temperatureEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_TEMPERATURE)
        effect!!.setParameter("scale", factor)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun tintEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_FISHEYE)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun rotateEffect(factor : Float = 0.5f) {
        val factory = effectContext!!.factory
        effect = factory.createEffect(EffectFactory.EFFECT_ROTATE)

        angle += 90
        angle %= 360

        GLES20.glViewport(0,0,mViewWidth, mViewHeight)
        GLES20.glClearColor(0f,0f,0f,1f)

        if(angle==0 || angle==180) {
            photo = getResizedBitmap(photo!!,mViewWidth,mViewHeight)
            photoWidth = photo!!.width
            photoHeight = photo!!.height
        }
        else {
            photo = getResizedBitmap(photo!!,mViewWidth,mViewWidth)
            photoWidth = photo!!.width
            photoHeight = photo!!.width
        }

        var coords = computeOutputVertices()
        generateSquare(coords)

        effect!!.setParameter("angle", angle)
        effect!!.apply(textures[0], photoWidth, photoHeight, textures[1])
    }

    private fun computeOutputVertices() : FloatArray {
        Log.e("ZONE_DEBUG", "computeOutputVertices")
        var photoWidth1 : Float = photoWidth * 1.0f
        var photoHeight1 : Float = photoHeight * 1.0f
        val imgAspectRatio = photoWidth1 / photoHeight1
        var mViewWidth1 : Float = mViewWidth * 1.0f
        var mViewHeight1 : Float = mViewHeight * 1.0f
        val viewAspectRatio = mViewWidth1/ mViewHeight1
        val relativeAspectRatio = viewAspectRatio / imgAspectRatio
        var x0: Float
        var y0: Float
        var x1: Float
        var y1: Float
        if (relativeAspectRatio > 1.0f) {
            x0 = -1.0f / relativeAspectRatio
            y0 = -1.0f
            x1 = 1.0f / relativeAspectRatio
            y1 = 1.0f
        } else {
            x0 = -1.0f
            y0 = -relativeAspectRatio
            x1 = 1.0f
            y1 = relativeAspectRatio
        }

        if(photoHeight < mViewHeight) {
            x0 = -1.0f
            y0 = -relativeAspectRatio + 0.5f
            x1 = 1.0f
            y1 = relativeAspectRatio + 0.5f
        }
        return floatArrayOf(x0, y0, x1, y0, x0, y1, x1, y1)
//        return floatArrayOf(0f, 0f, 1f, 0f, 0f, 1f, 1f, 1f)
    }

    fun getResizedBitmap(image: Bitmap, bitmapWidth: Int, bitmapHeight: Int): Bitmap {
        Log.e("ZONE_DEBUG", "getResizedBitmap")
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true)
    }
}