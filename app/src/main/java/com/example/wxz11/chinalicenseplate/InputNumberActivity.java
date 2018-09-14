package com.example.wxz11.chinalicenseplate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class InputNumberActivity extends AppCompatActivity implements VehicleBoardView.OnVehicleKeyListener, View.OnClickListener {
    TextView btnNext;
    ImageView ivAdd;
    LinearLayout lltNumber;
    VehicleBoardView vehicleView;
    TextView tvId1;
    TextView tvId2;
    TextView tvId3;
    TextView tvId4;
    TextView tvId5;
    TextView tvId6;
    TextView tvId7;
    TextView tvId8;
    private int selected = 0;
    private int idIndex = -1;
    private boolean isNew = false;
    private String[] vehicleID = new String[8];
    private SparseArray<TextView> viewMap = new SparseArray<>(8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_number);
        findViews();
        vehicleView.setKeyListener(this);
        viewMap.put(0, tvId1);
        viewMap.put(1, tvId2);
        viewMap.put(2, tvId3);
        viewMap.put(3, tvId4);
        viewMap.put(4, tvId5);
        viewMap.put(5, tvId6);
        viewMap.put(6, tvId7);
        viewMap.put(7, tvId8);
    }

    private void findViews() {
        btnNext = findViewById(R.id.btn_next);
        ivAdd = findViewById(R.id.iv_add);
        lltNumber = findViewById(R.id.llt_number);
        vehicleView = findViewById(R.id.kbv);
        tvId1 = findViewById(R.id.tv_id1);
        tvId2 = findViewById(R.id.tv_id2);
        tvId3 = findViewById(R.id.tv_id3);
        tvId4 = findViewById(R.id.tv_id4);
        tvId5 = findViewById(R.id.tv_id5);
        tvId6 = findViewById(R.id.tv_id6);
        tvId7 = findViewById(R.id.tv_id7);
        tvId8 = findViewById(R.id.tv_id8);
        findViewById(R.id.rlt_id8).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvId1.setOnClickListener(this);
        tvId2.setOnClickListener(this);
        tvId3.setOnClickListener(this);
        tvId4.setOnClickListener(this);
        tvId5.setOnClickListener(this);
        tvId6.setOnClickListener(this);
        tvId7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_id1:
                changeColor("0");
                if (isAllNotEmpty()) {
                    if (vehicleView.getState() != VehicleBoardView.STATE_AREA) {
                        vehicleView.changeBoard(false);
                    }

                    selected = 0;
                    idIndex = -1;
                }
                break;
            case R.id.tv_id2:
                if (isAllNotEmpty()) {
                    changeColor("1");
                    if (vehicleView.getState() != VehicleBoardView.STATE_ENGLISH) {
                        vehicleView.changeBoard(true, false);
                    }
                    selected = 1;
                    idIndex = 0;
                }
                break;
            case R.id.tv_id3:
                if (isAllNotEmpty()) {
                    changeColor("2");
                    checkNumberBoard();
                    selected = 2;
                    idIndex = 1;
                }
                break;
            case R.id.tv_id4:
                if (isAllNotEmpty()) {
                    changeColor("3");

                    selected = 3;
                    idIndex = 2;
                    checkNumberBoard();
                }
                break;
            case R.id.tv_id5:
                if (isAllNotEmpty()) {
                    changeColor("4");

                    selected = 4;
                    idIndex = 3;
                    checkNumberBoard();
                }
                break;
            case R.id.tv_id6:
                if (isAllNotEmpty()) {
                    changeColor("5");

                    selected = 5;
                    idIndex = 4;
                    checkNumberBoard();
                }
                break;
            case R.id.tv_id7:
                if (isAllNotEmpty()) {
                    changeColor("6");

                    selected = 6;
                    idIndex = 5;
                    checkNumberBoard();
                }
                break;
            case R.id.rlt_id8:
                if (TextUtils.isEmpty(vehicleID[7])) {
                    isNew = !isNew;
                    if (isNew) {
                        if (selected == 6)
                            selected++;
                    } else {
                        if (selected == 7)
                            selected--;
                    }
                    v.setBackgroundResource(isNew ? R.drawable.bg_vehicle_number_normal : R.drawable.bg_vehicle_number_dash);
                    ivAdd.setVisibility(isNew ? View.GONE : View.VISIBLE);
                    tvId8.setTextColor(Color.parseColor("#acacac"));
                    tvId8.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8);
                } else {
                    changeColor("7");
                    checkNumberBoard();
                }
                break;
            case R.id.btn_next:
//                Intent intent = new Intent();
//                intent.putExtra("carNumber", getCarNumber());
//                setResult(Activity.RESULT_OK, intent);
//                finish();
                Toast.makeText(this, getCarNumber(), Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_back:
//                finish();
                Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String getCarNumber() {
        StringBuilder sb = new StringBuilder();
        for (String s : vehicleID) {
            if (TextUtils.isEmpty(s)) {
                continue;
            }
            sb.append(s);
        }
        return sb.toString();
    }

    private void checkNumberBoard() {
        if (vehicleView.getState() != VehicleBoardView.STATE_NUMBER) {
            vehicleView.changeBoard(true, true);
        }
    }

    private void changeColor(String s) {
        for (int i = 0; i < lltNumber.getChildCount(); i++) {
            View view = lltNumber.getChildAt(i);
            if (s.equals(view.getTag())) {
                view.setBackgroundResource(R.drawable.bg_vehicle_number_selected);
            } else {
                if (view instanceof ImageView) {
                    continue;
                }
                view.setBackgroundResource(R.drawable.bg_vehicle_number_normal);
            }
        }
    }

    public boolean isAllNotEmpty() {
        int count = isNew ? 7 : 6;
        for (int i = 0; i <= count; i++) {
            if (TextUtils.isEmpty(vehicleID[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onVehicleKeyUp(String key) {
        if (isNew && idIndex == 6) {
            tvId8.setTextColor(Color.parseColor("#4a4a4a"));
            tvId8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//            tvId8.setText("");
        }
//        if (isNew) {
//            if (selected > 7) {
//                selected = 7;
//            }
//        } else {
//            if (selected > 6) {
//                selected = 6;
//            }
//        }
        idIndex++;
        if (isNew) {
            if (idIndex > 7) {
                idIndex = 7;
            }
        } else {
            if (idIndex > 6) {
                idIndex = 6;
            }
        }
        viewMap.get(idIndex).setText(key);
        vehicleID[idIndex] = key;
        selected++;
        if (selected == 1) {
            vehicleView.changeBoard(true, false);
        } else {
            vehicleView.changeBoard(true, true);
        }
        if (isNew) {
            if (selected > 7) {
                selected = 7;
            }
        } else {
            if (selected > 6) {
                selected = 6;
            }
        }
        changeColor(String.valueOf(selected));
        changeNextBtnState();
    }

    private void changeNextBtnState() {
        //不管是不是新能源车牌，只要前6位全部不空，就可以
        btnNext.setEnabled(check());
    }

    private boolean check() {
        for (int i = 0; i <= 6; i++) {
            if (TextUtils.isEmpty(vehicleID[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onVehicleKeyDelete() {
        if (idIndex < 0) {
            idIndex = 0;
        }
        viewMap.get(idIndex).setText("");
        vehicleID[idIndex] = "";
        changeColor(String.valueOf(idIndex));
        if (selected == idIndex) {
            idIndex--;
        } else {
            selected--;
            idIndex--;
        }
        if (idIndex != 7) {
            if (isNew) {
                tvId8.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8);
                tvId8.setTextColor(Color.parseColor("#acacac"));
                tvId8.setText("新能源");
            }
        }
        if (selected < 0) {
            selected = 0;
        }
        if (idIndex < -1) {
            selected = -1;
        }
        if (selected == 0) {
            vehicleView.changeBoard(false);
        } else if (selected == 1) {
            vehicleView.changeBoard(true, false);
        } else {
            vehicleView.changeBoard(true, true);
        }
        changeNextBtnState();
    }

}
