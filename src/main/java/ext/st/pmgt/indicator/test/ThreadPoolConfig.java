//package ext.st.pmgt.indicator.test;
//
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.ThreadPoolExecutor;
//
//@Configuration
//@EnableAsync
//@EnableScheduling
//public class ThreadPoolConfig implements AsyncConfigurer {
//
//    private static final int CORE_POOL_SIZE = 1;
//    private static final int MAX_POOL_SIZE = 10;
//    private static final int QUEUE_CAPACITY = 100;
//
//    @Bean("taskExecutor")
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
//        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
//        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
//        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        taskExecutor.setThreadNamePrefix("taskExecutor-async");
//        taskExecutor.setKeepAliveSeconds(1000);
//        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//        taskExecutor.setAwaitTerminationSeconds(60);
//        taskExecutor.initialize();
//        return taskExecutor;
//    }
//
//
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return null;
//    }
//
//}
