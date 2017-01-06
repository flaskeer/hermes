package com.asterisk.opensource.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;


@Slf4j
public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor,InitializingBean,DisposableBean{

    private AsyncTaskExecutor executor;

    public ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void destroy() throws Exception {
        if (executor instanceof DisposableBean) {
            DisposableBean bean = (DisposableBean) executor;
            bean.destroy();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (executor instanceof InitializingBean) {
            InitializingBean bean = (InitializingBean) executor;
            bean.afterPropertiesSet();
        }
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        executor.execute(wrapperRunnable(task),startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return executor.submit(wrapperRunnable(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(wrapperCallable(task));
    }

    @Override
    public void execute(Runnable task) {
        executor.execute(wrapperRunnable(task));
    }

    private Runnable wrapperRunnable(final Runnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                handle(e);
            }
        };
    }

    protected void handle(Exception e) {
        log.error("Caught async exception",e);
    }

    private <T> Callable<T> wrapperCallable(Callable<T> callable) {
        return () -> {
          try {
                return callable.call();
          } catch (Exception e) {
              handle(e);
              throw e;
          }
        };
    }
}
