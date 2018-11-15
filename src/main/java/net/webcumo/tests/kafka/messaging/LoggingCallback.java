package net.webcumo.tests.kafka.messaging;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class LoggingCallback<T> implements ListenableFutureCallback<SendResult<String, T>> {
    @Override
    public void onFailure(Throwable ex) {
        System.err.println("Error: " + ex.getMessage());
    }

    @Override
    public void onSuccess(SendResult<String, T> result) {
        System.out.println("Service: message id=" + result.getProducerRecord().key()
                + " value=`" + result.getProducerRecord().value() + "` were sent");
    }
}
