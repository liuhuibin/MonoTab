package com.huibin.monotab.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.huibin.monotab.R;


/**
 * Created by huibin on 15/9/16.
 */
public class ViewPagerIndicator extends View {


    /**indicator的线的颜色*/
    private int mLineColor ;

    /**indicator的线的宽度*/
    private int mBorderWidth ;

    /**indicator的等腰三角形底边的长度*/
    private int mBase ;

    /**indicator的等腰三角形的腰长*/
    private int mLeg ;

    /**indicator的三角形顶点的位置*/
    private int mVertex ;

    private Paint mPaint ;

    /**控件的宽度*/
    private int mWidth ;

    /**控件的高度*/
    private int mHeight ;

    private ViewPager mViewPager ;


    private final int DEFAULT_LINE_COLOR = Color.parseColor("#333333");
    private final int DEFAULT_BORDER_WIDTH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()) ;
    private final int DEFAULT_BASE = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics()) ;
    private final int DEFAULT_LEG = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics()) ;
    private final int DEFAULT_VERTEX = (DEFAULT_BASE+2)/2 ;

    public ViewPagerIndicator(Context context) {
        this(context,null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArrowViewPagerIndicator) ;
        try {
            mLineColor = a.getColor(R.styleable.ArrowViewPagerIndicator_lineColor,DEFAULT_LINE_COLOR) ;
            mBorderWidth = (int) a.getDimension(R.styleable.ArrowViewPagerIndicator_borderWidth, DEFAULT_BORDER_WIDTH);
            mBase = (int) a.getDimension(R.styleable.ArrowViewPagerIndicator_base,DEFAULT_BASE);
            mLeg = (int) a.getDimension(R.styleable.ArrowViewPagerIndicator_leg,DEFAULT_LEG);
            mVertex = (int) a.getDimension(R.styleable.ArrowViewPagerIndicator_vertex,DEFAULT_VERTEX) ;

            if (mLeg*2<=mBase) throw new RuntimeException("leg*2必须要大于base的大小") ;

        } finally {
            a.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mLineColor);

        setWillNotDraw(false);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w ;
        mHeight = h ;
        mVertex = mWidth/4 ;
        if (mVertex<mBase/2 ) mVertex = mBase/2;

        if(mVertex>(mWidth-mBase/2)) mVertex = mWidth-mBase/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

//        Log.e("TAG", "width:" + mWidth + " height:" + mHeight + " base:" + mBase + " leg:" + mLeg) ;
        int halfBase = mBase/2 ;
        float height = (int) (mHeight*0.85f);
        canvas.drawLine(0,height,mVertex-halfBase,height,mPaint);
        canvas.drawLine(mVertex-halfBase,height,mVertex, (float) (height-Math.pow(mLeg * mLeg - halfBase * halfBase, .5f)),mPaint);
        canvas.drawLine(mVertex, (float) (height-Math.pow(mLeg * mLeg - halfBase * halfBase, .5f)),mVertex+halfBase,height,mPaint);
        canvas.drawLine(mVertex + halfBase, height, mWidth, height, mPaint);

    }

    public void setVertex(int vertex) {
        mVertex = vertex ;
        invalidateView();
    }

    public void setStartPosition() {
        setVertex(mWidth/4);
        if (mViewPager!=null) {
            mViewPager.setCurrentItem(0,true);
        }
    }

    public void setEndPosition(){
        setVertex(mWidth*3/4);
        if (mViewPager!=null) {
            mViewPager.setCurrentItem(1,true);
        }
    }

    public void bindViewPager(ViewPager viewPager) {
        mViewPager = viewPager ;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                if (positionOffset>0) {
                    setVertex((int) (mWidth / 4 + mWidth / 2 * positionOffset));
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (0==position) {
                    setVertex(mWidth/4);
                } else if (1==position) {
                    setVertex((int) (mWidth*.75f));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }





    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
