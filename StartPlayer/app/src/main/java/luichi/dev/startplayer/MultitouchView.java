package luichi.dev.startplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MultitouchView  extends View implements View.OnTouchListener {
	private Paint paint;
	private static final int SIZE = 150;
	private boolean modeSelectStartPlayer = false;
	private int[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY, Color.LTGRAY, Color.YELLOW};
	Map<Integer, Vector2D> map = new HashMap<Integer, Vector2D>();
	private CountDownTimer cTimer = null;
	public MultitouchView(Context context){
		super(context);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setARGB(200, 255, 255, 255 );
		//setFocusable(true);
		this.setOnTouchListener(this);
		setBackgroundColor(getResources().getColor(R.color.black));
	}

	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		Iterator it = map.keySet().iterator();
		int index = 0;

		if(!modeSelectStartPlayer){
			while(it.hasNext()) {
				Integer key = (Integer) it.next();
				int x = map.get(key).GetPosX();
				int y = map.get(key).GetPosY();
				paint.setColor(colors[index % colors.length]);
				canvas.drawCircle(x, y, SIZE, paint);
				index++;
			}
		} else {
			int min = 1;
			int max = map.size();
			Random random = new Random();
			int startPlayer  = random.nextInt(max + min) + min;
			while(it.hasNext()) {
				Integer key = (Integer) it.next();
				int x = map.get(key).GetPosX();
				int y = map.get(key).GetPosY();
				if(index == startPlayer){
					paint.setColor(Color.RED);
				} else {
					paint.setColor(Color.WHITE);
				}
				canvas.drawCircle(x, y, SIZE, paint);
				index++;
			}
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		int pointerIndex = motionEvent.getActionIndex();
		int pointerID = motionEvent.getPointerId(pointerIndex);
		int maskedAction = motionEvent.getActionMasked();

		switch (maskedAction) {
			case MotionEvent.ACTION_POINTER_DOWN:
			case MotionEvent.ACTION_DOWN: {
				//TODO: registar el touch con el id y pos
				int x = (int) motionEvent.getX(pointerIndex);
				int y = (int) motionEvent.getY(pointerIndex);
				Vector2D newPoint = new Vector2D(x, y);
				map.put(pointerID, newPoint);
				if(cTimer != null){
					cTimer.cancel();
				}

				cTimer = new CountDownTimer(3000, 1000) {
					@Override
					public void onTick(long l) {

					}

					@Override
					public void onFinish() {
						modeSelectStartPlayer = true;
						invalidate();
					}
				};
				cTimer.start();
			}
				break;
			case MotionEvent.ACTION_MOVE: {
				//TODO: actulizar la pos del touch con el id
				int size = motionEvent.getPointerCount();
				for (int i = 0; i < size; i++) {
					Vector2D point = map.get(motionEvent.getPointerId(i));
					if (point != null) {
						point.SetPosX((int) motionEvent.getX(i));
						point.SetPosY((int) motionEvent.getY(i));
					}
				}
				int x = (int) motionEvent.getX(pointerIndex);
				int y = (int) motionEvent.getY(pointerIndex);
				map.get(pointerID).SetPosX(x);
				map.get(pointerID).SetPosY(y);
			}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL: {
				//TODO: eliminar el touch con el id
				map.remove(pointerID);
			}
				break;
		}

		invalidate();

		return true;
	}
}
