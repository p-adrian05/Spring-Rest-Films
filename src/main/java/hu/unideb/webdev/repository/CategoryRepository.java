package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {


}
