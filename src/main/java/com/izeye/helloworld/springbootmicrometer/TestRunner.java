package com.izeye.helloworld.springbootmicrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.okhttp3.OkHttpMetricsEventListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * {@link ApplicationRunner} for testing.
 *
 * @author Johnny Lim
 */
@Component
public class TestRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    private final MeterRegistry meterRegistry;

    public TestRunner(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .eventListener(OkHttpMetricsEventListener.builder(this.meterRegistry, "okhttp.requests")
                        .tags(Tags.of("foo", "bar"))
                        .build())
                .build();

        Request request = new Request.Builder().url("https://httpbin.org/get").build();
        Call call = client.newCall(request);
        Response response = call.execute();
        log.info("response: {}", response);
    }

}
