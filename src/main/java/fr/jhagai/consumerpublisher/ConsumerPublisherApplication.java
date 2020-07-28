package fr.jhagai.consumerpublisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class ConsumerPublisherApplication {

    public static void main(String[] args) {

        final ConfigurableApplicationContext run = SpringApplication.run(ConsumerPublisherApplication.class, args);

        final MyEntityService bean = run.getBean(MyEntityService.class);
        final ThroughputMeasure measure = run.getBean(ThroughputMeasure.class);

        ExecutorService executorInserts = Executors.newFixedThreadPool(1);

        executorInserts.execute(
                () -> {
                    while (true) {
                        MyEntity myEntity = new MyEntity();
                        bean.save(myEntity);
                        log.info("Saved {}", myEntity.getId());
                        measure.saved.incrementAndGet();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        int nbThreads = 2;

        ExecutorService executorProcess = Executors.newFixedThreadPool(nbThreads);

        for (int i = 0; i < nbThreads; i++) {
            executorProcess.execute(
                    () -> {
                        while (true) {
                            log.info("---- START ----");
                            bean.process();
                            measure.processed.incrementAndGet();
                            log.info("---- END ----");
                        }
                    }
            );
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("----- MEASURES ------");
        log.info("SAVED {}", measure.saved.get());
        log.info("PROCESSED {}", measure.processed.get());

        System.exit(0);
    }

}
