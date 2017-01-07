/**
 * @author donghao
 * @Date 17/1/7
 * @Time 下午5:33
 *  调度模块  这里去做两件事情
 *  1. 把传入的URL 推到MQ里
 *  2. 解析完之后的数据推倒MQ里 交给下游去消费
 */
package com.asterisk.opensource.spider.schedule;