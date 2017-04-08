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
 * Bold Text with custom font face 'FontAssetUtils.FONT_BOLD'
 */

public class TextViewBold extends TextView {


    public TextViewBold(Context context) {
      super(context);
		init();
    }

    public TextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
		init();
    }

    public TextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		init();
    }

    private void init() {
		// TODO Auto-generated method stub
    	super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FontAssetUtils.FONT_BOLD));
	}

	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}