package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.LanguageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguageRepository extends CrudRepository<LanguageEntity,Integer> {

        Optional<LanguageEntity> findByName(String name);
}
