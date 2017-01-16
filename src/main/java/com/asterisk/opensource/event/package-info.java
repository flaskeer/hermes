/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 10:34
 * @Description 我觉得理想的思路应该是；
 * 1. 用来做实时推送   在某些情况下直接发事件通知给MQ
 * 2. 把事件收集起来做event sourcing  事件驱动的系统 依靠事件去做一个串联
 * @Since 1.0.0
 */
package com.asterisk.opensource.event;