package learningtest.io.micrometer.core.instrument.binder.jvm;

import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Tests for {@link ExecutorServiceMetrics}.
 *
 * @author Johnny Lim
 */
class ExecutorServiceMetricsTests {

    @Test
    void monitorWithThreadPoolTaskExecutor() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.afterPropertiesSet();

        Executor executor = ExecutorServiceMetrics.monitor(registry, taskExecutor, "my.executor");

        String meterName = "executor.execution";
        assertThat(registry.find(meterName).timer().count()).isEqualTo(0);

        CountDownLatch latch = new CountDownLatch(1);
        executor.execute(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        assertThat(registry.find(meterName).timer().count()).isEqualTo(0);

        latch.countDown();
        await().untilAsserted(() -> assertThat(registry.find(meterName).timer().count()).isEqualTo(1));
    }

}
