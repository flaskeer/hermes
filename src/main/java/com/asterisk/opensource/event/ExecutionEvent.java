package com.asterisk.opensource.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 9:39
 * @Description
 * @Since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionEvent {


    private String id;

    private EventExecutionType type = EventExecutionType.EXECUTE_FAILURE;





}
