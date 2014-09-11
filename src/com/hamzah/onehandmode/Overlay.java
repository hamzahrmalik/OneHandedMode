package com.hamzah.onehandmode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;

public class Overlay extends View {
	
    public Overlay (Context context) {
		super(context);
	}

	Rect rect = new Rect();
	
    @Override
    public void onDraw(Canvas canvas) {
        int l = getPaddingLeft();
        int t = getPaddingTop();
        int r = getWidth() - getPaddingRight();
        int b = getHeight() - getPaddingBottom();
        rect.set(l, t, r, b);

        canvas.save();
        canvas.clipRect(rect, Region.Op.DIFFERENCE);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.restore();

        super.onDraw(canvas);
    }
}