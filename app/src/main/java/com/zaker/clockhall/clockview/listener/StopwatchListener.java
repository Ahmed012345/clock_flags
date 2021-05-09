package com.zaker.clockhall.clockview.listener;


import com.zaker.clockhall.clockview.enumeration.StopwatchState;
import com.zaker.clockhall.clockview.model.StopwatchSavedItem;

public interface StopwatchListener {

    void onStopwatchStateChanged(StopwatchState stopwatchState);

    void onStopwatchSaveValue(StopwatchSavedItem stopwatchSavedItem);
}
