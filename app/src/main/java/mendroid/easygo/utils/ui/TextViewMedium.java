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
 * Bold Text with custom font face 'FontAssetUtils.FONT_MEDIUM'
 */

public class TextViewMedium extends TextView {


    public TextViewMedium(Context context) {
      super(context);
		init();
    }

    public TextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
		init();
    }

    public TextViewMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		init();
    }

    private void init() {
		// TODO Auto-generated method stub
    	super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FontAssetUtils.FONT_MEDIUM));
	}

	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }

}