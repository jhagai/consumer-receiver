package fr.jhagai.consumerpublisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class MyEntityService {

    @Autowired
    private MyEntityRepository myEntityRepository;

    @Transactional
    public void save(MyEntity e) {
        this.myEntityRepository.save(e);
    }

    @Transactional
    public void process() {
        List<MyEntity> unlocked = this.myEntityRepository.findUnlocked(1);

        log.info("Retrieved nb: {}", unlocked.size());
        unlocked.stream().forEach(u -> {
            log.info("Processing {}", u.getId());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Processed {}", u.getId());
            myEntityRepository.delete(u);
            log.info("Deleted {}", u.getId());
        });
    }

}
