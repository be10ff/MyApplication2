package ru.abelov.myapplication.ui
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.*
import ru.abelov.myapplication.R

class CompassView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private val uiSccope = CoroutineScope(Dispatchers.Main + Job())
    private val arrow = BitmapFactory.decodeResource(resources, R.drawable.arrow)
    private val arrowRect = Rect(0, 0, arrow.width, arrow.height)
    private var angle = 0


    init {
        holder.addCallback(this)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        setZOrderOnTop(true)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        uiSccope.coroutineContext.cancel()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        uiSccope.launch {
            while (isActive) {
                drawCompass(holder)
//                angle++
            }
        }
    }

    public fun set(angle : Int) {
        this.angle = angle
    }

    suspend fun drawCompass(holder: SurfaceHolder) {
        delay(300)
        val canvas: Canvas? = holder.lockCanvas()
        canvas?.let {
            it.drawColor(0, PorterDuff.Mode.CLEAR)
            it.rotate(angle%360f, it.width / 2f, it.height / 2f)
            it.drawBitmap(arrow, arrowRect, Rect(0, 0, it.width, it.height), null)
            holder.unlockCanvasAndPost(it)
        }
    }

}

