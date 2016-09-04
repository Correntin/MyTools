package fr.correntin.android.mytools.common.threads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by corentin on 26/12/15.
 */
public class ThreadPool<THREAD_POOL_PARAM, THREAD_POOL_RETURN>
{
    private int maxThreads = 1;
    private ThreadPoolTaskListener threadPoolListener;

    private List<ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN>> threadPoolTasks;
    private List<THREAD_POOL_PARAM> waitingQueue = new ArrayList<>();

    private OnThreadPoolTaskWaitingHandler onThreadPoolTaskWaitingHandler;

    private ThreadPool(final int maxThreads)
    {
        this.maxThreads = maxThreads;
        this.threadPoolTasks = new ArrayList<>();
        this.onThreadPoolTaskWaitingHandler = new OnThreadPoolTaskWaitingHandler();

        for (int i = 0; i < maxThreads; i++)
        {
            final ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN> threadPoolTask = new ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN>(this);
            threadPoolTask.start();
            this.threadPoolTasks.add(threadPoolTask);
        }
    }

    public static ThreadPool create(final int maxThreads)
    {
        return new ThreadPool(maxThreads);
    }

    public void registerListener(ThreadPoolTaskListener threadPoolListener)
    {
        this.threadPoolListener = threadPoolListener;
        for (ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN> task : threadPoolTasks)
            task.registerListener(threadPoolListener);

    }

    public void execute(THREAD_POOL_PARAM threadPoolParam)
    {
        boolean launched = false;
        Log.d("CORENTIN", "execute: BEFORE");
        for (ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN> task : threadPoolTasks)
        {
            if (task.isWaiting())
            {
                launched = true;
                task.execute(threadPoolParam);
                break;
            }
        }

        if (launched == false)
            waitingQueue.add(threadPoolParam);
        Log.d("CORENTIN", "execute: AFTER");
    }

    void onThreadPoolTaskWaitingInternal(ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN> threadPoolTask)
    {
       if (this.waitingQueue.size() <= 0)
           return;

        final THREAD_POOL_PARAM paramValue = this.waitingQueue.get(0);
        this.waitingQueue.remove(0);
        threadPoolTask.execute(paramValue);
    }

    public void onThreadPoolTaskWaiting(ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN> threadPoolTask)
    {
        this.onThreadPoolTaskWaitingHandler.sendMessageDelayed(
                this.onThreadPoolTaskWaitingHandler.obtainMessage(0, threadPoolTask),
                100
        );
    }

    public class OnThreadPoolTaskWaitingHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN> threadPoolTask = null;

            if (msg.obj != null)
                threadPoolTask = (ThreadPoolTask<THREAD_POOL_PARAM, THREAD_POOL_RETURN>) msg.obj;
            onThreadPoolTaskWaitingInternal(threadPoolTask);
        }
    }
}
