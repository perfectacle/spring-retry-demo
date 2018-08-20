package com.example.retry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetryApplicationTests {
    @Inject
    private RetryService service;

    @Test
    public void contextLoads() {
    }

    @Test(expected = ExhaustedRetryException.class)
    public void retry() {
        service.retry();
    }

    @Test
    public void retryWithRecovery() {
        service.retryWithRecover();
    }

    @Test(expected = RuntimeException.class)
    public void retryWithTemplate() {
        service.retryWithTemplate();
    }

    @Test
    public void retryWithTemplateAndRecovery() {
        service.retryWithTemplateAndRecovery();
    }
}
