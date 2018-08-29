package com.iloomo.model;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by wpt on 2017/5/25.
 */

public interface MOnItemClickListener {
    void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
