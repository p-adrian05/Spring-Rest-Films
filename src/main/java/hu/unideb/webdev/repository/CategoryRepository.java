package hu.unideb.webdev.repository;

import hu.unideb.webdev.repository.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {

    @Query("select c from CategoryEntity c join FilmCategoryEntity f " +
            "on c.id = f.id.categoryId where f.id.filmId = ?1")
    List<CategoryEntity> findByFilmId(int filmId);
    Optional<CategoryEntity> findByName(String name);
    boolean existsCategoryEntityByName(String name);
}
