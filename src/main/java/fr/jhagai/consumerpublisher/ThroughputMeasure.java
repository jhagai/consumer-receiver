package fr.jhagai.consumerpublisher;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ThroughputMeasure {

    public AtomicInteger saved = new AtomicInteger();
    public AtomicInteger processed = new AtomicInteger();
}
