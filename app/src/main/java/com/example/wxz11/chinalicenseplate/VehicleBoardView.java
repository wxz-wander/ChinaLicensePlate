package com.example.wxz11.chinalicenseplate;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.wxz11.chinalicenseplate.R;

/**
 * Created by wxz11 on 2018/3/21.
 */

public class VehicleBoardView extends FrameLayout {
    public static final int STATE_AREA = 1, STATE_ENGLISH = 2, STATE_NUMBER = 3;
    private View areaView;
    private View numberView;
    private int state = STATE_AREA;

    public VehicleBoardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VehicleBoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VehicleBoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VehicleBoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        areaView = inflate(context, R.layout.item_area_layout, null);
        numberView = inflate(context, R.layout.item_number_layout, null);
        bindContainerView((LinearLayout) areaView.findViewById(R.id.llt_area1));
        bindContainerView((LinearLayout) areaView.findViewById(R.id.llt_area2));
        bindContainerView((LinearLayout) areaView.findViewById(R.id.llt_area3));
        bindContainerView((LinearLayout) areaView.findViewById(R.id.llt_area4));
        bindContainerView((LinearLayout) numberView.findViewById(R.id.llt_number1));
        bindContainerView((LinearLayout) numberView.findViewById(R.id.llt_number2));
        bindContainerView((LinearLayout) numberView.findViewById(R.id.llt_number3));
        bindContainerView((LinearLayout) numberView.findViewById(R.id.llt_number4));
        bindView(areaView.findViewById(R.id.iv_delete));
        bindView(numberView.findViewById(R.id.iv_delete));
        addView(areaView);
    }

    private void bindView(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyListener != null) {
                    keyListener.onVehicleKeyDelete();
                }
            }
        });
    }

    public void changeBoard(boolean isNumber, boolean numberEnable) {
        removeAllViews();
        if (isNumber) {
            if (numberEnable) {
                state = STATE_NUMBER;
            } else {
                state = STATE_ENGLISH;
            }
            enableViews((LinearLayout) numberView.findViewById(R.id.llt_number1), numberEnable);
            addView(numberView);
        } else {
            addView(areaView);
            state = STATE_AREA;
        }
    }

    public void changeBoard(boolean isNumber) {
        changeBoard(isNumber, false);
    }

    private void enableViews(LinearLayout container, boolean numberEnable) {
        for (int i = 0; i < container.getChildCount(); i++) {
            container.getChildAt(i).setEnabled(numberEnable);
        }
    }

    private void bindContainerView(ViewGroup container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View childAt = container.getChildAt(i);
            if (childAt instanceof Button) {
                final Button btn = (Button) childAt;
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (keyListener != null) {
                            keyListener.onVehicleKeyUp(btn.getText().toString().trim());
                        }
                    }
                });
            }
        }
    }

    private OnVehicleKeyListener keyListener;

    public OnVehicleKeyListener getKeyListener() {
        return keyListener;
    }

    public void setKeyListener(OnVehicleKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public interface OnVehicleKeyListener {
        void onVehicleKeyUp(String key);

        void onVehicleKeyDelete();
    }
}
