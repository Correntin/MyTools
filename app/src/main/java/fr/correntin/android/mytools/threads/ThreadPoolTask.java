package fr.correntin.android.mytools.threads;

import android.util.Log;

/**
 * Created by corentin on 26/12/15.
 */
public class ThreadPoolTask<THREAD_POOL_TASK_PARAM, THREAD_POOL_TASK_RETURN> extends Thread
{
    private ThreadPoolTaskListener<THREAD_POOL_TASK_PARAM, THREAD_POOL_TASK_RETURN> threadPoolListener;
    private boolean started = false;
    private boolean running = true;
    private boolean waiting = false;
    private final Object waitingObject = new Object();
    private ThreadPool<THREAD_POOL_TASK_PARAM, THREAD_POOL_TASK_RETURN> threadPool;

    private THREAD_POOL_TASK_PARAM paramValue;

    public ThreadPoolTask(ThreadPool<THREAD_POOL_TASK_PARAM, THREAD_POOL_TASK_RETURN> threadPool)
    {
        this.threadPool = threadPool;
    }

    public void registerListener(ThreadPoolTaskListener<THREAD_POOL_TASK_PARAM, THREAD_POOL_TASK_RETURN> threadPoolListener)
    {
        this.threadPoolListener = threadPoolListener;
    }

    public void execute(THREAD_POOL_TASK_PARAM paramValue)
    {
        this.paramValue = paramValue;
        this.resuming();
    }

    public boolean isWaiting()
    {
        return this.waiting;
    }

    private void waiting()
    {
        synchronized (this.waitingObject)
        {
            try
            {
                Log.d("CORENTIN", "waiting: " + Thread.currentThread().getId());
                this.waiting = true;
                this.threadPool.onThreadPoolTaskWaiting(this);
                this.waitingObject.wait();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void resuming()
    {
        synchronized (this.waitingObject)
        {
            Log.d("CORENTIN", "resuming: " + Thread.currentThread().getId());
            this.waiting = false;
            this.waitingObject.notify();
        }
    }

    private void doTask()
    {
        this.threadPoolListener.onThreadPoolTaskStarted(this.paramValue);

        Log.d("CORENTIN", "doTask: BEFORE(" + paramValue + ") " + Thread.currentThread().getId());
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        Log.d("CORENTIN", "doTask: AFTER(" + paramValue + ") " + Thread.currentThread().getId());

        this.threadPoolListener.onThreadPoolTaskFinished(null);
    }

    @Override
    public void run()
    {
        this.started = true;

        this.waiting();

        while (this.running == true)
        {
            this.resuming();

            this.doTask();

            this.waiting();
        }

    }
}
