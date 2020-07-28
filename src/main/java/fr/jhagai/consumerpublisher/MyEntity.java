package fr.jhagai.consumerpublisher;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
// @NamedQuery(name = "MyEntity.all", query = "SELECT e FROM MyEntity e", hints = {@QueryHint(name = "javax.persistence.lock.timeout", value = "-2")})
public class MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
