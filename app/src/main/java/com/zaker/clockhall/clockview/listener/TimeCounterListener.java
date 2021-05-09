package com.zaker.clockhall.clockview.listener;


import com.zaker.clockhall.clockview.enumeration.TimeCounterState;

public interface TimeCounterListener {

    void onTimeCounterCompleted();

    void onTimeCounterStateChanged(TimeCounterState timeCounterState);

}
