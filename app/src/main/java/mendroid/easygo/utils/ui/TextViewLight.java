package mendroid.easygo.utils.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * @author Mendroid
 */

/**
 * Bold Text with custom font face 'FontAssetUtils.FONT_LIGHT'
 */

public class TextViewLight extends TextView {


    public TextViewLight(Context context) {
      super(context);
		init();
    }

    public TextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
		init();
    }

    public TextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		init();
    }

    private void init() {
		// TODO Auto-generated method stub
    	super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FontAssetUtils.FONT_LIGHT));
	}

	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}