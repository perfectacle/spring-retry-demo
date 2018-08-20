package com.example.retry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.UnknownFormatConversionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetryService {
    private final RetryTemplate retryTemplate;

    @Retryable(value = {Exception.class}, maxAttempts = 4, backoff = @Backoff(delay = 2000L))
    void retry() {
        log.info("시도 냥냥!");
        throw new IllegalArgumentException("");
    }

    @Retryable(value = {Exception.class}, maxAttempts = 4, backoff = @Backoff(delay = 2000L))
    void retryWithRecover() {
        log.info("시도 냥냥!");
        throw new UnsupportedOperationException("");
    }

    @Recover
    void recover(final UnsupportedOperationException e) {
        log.info("시도 냥냥! 캐치 1", e);
    }

    // 두 번째 있는 놈이 먹힌당... 애초에 중복된 핸들러를 등록하면 안 되겠당.
    @Recover
    void recover2(final UnsupportedOperationException e) {
        log.info("시도 냥냥! 캐치 2", e);
    }

    @Recover
    void recover3(final UnsupportedOperationException e) {
        log.info("시도 냥냥! 캐치 3", e);
    }

    void retryWithTemplate() {
        retryTemplate.execute(retryContext -> {
            log.info("시도 냥냥3!");
            throw new RuntimeException();
        });
    }

    void retryWithTemplateAndRecovery() {
        retryTemplate.execute(retryContext -> {
            log.info("시도 냥냥4!");
            throw new RuntimeException();
        }, retryContext -> {
            log.info("그래도 실패했다냐? ㅉㅉ");
            return null;
        });
    }
}
