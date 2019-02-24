import android.content.Context
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.*
import kotlin.coroutines.*

class CompassView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var job : Job? = null

    init {
        holder.addCallback(this)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        setZOrderOnTop(true)
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        job?.cancelAndJoin()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        job = GlobalScope.launch {

            while (isActive) {
                drawCompass(holder)
            }
        }
    }

    suspend fun drawCompass(holder: SurfaceHolder) {

    }

}

