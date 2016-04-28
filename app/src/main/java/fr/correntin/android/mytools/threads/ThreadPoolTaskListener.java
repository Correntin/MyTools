package fr.correntin.android.mytools.threads;

/**
 * Created by corentin on 26/12/15.
 */
public interface ThreadPoolTaskListener<THREAD_POOL_TASK_PARAM, THREAD_POOL_TASK_RETURN>
{
    void onThreadPoolTaskStarted(THREAD_POOL_TASK_PARAM paramValue);
    void onThreadPoolTaskFinished(THREAD_POOL_TASK_RETURN returnValue);
}
