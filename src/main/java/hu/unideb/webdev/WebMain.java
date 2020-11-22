package hu.unideb.webdev;

import hu.unideb.webdev.repository.*;
import hu.unideb.webdev.repository.dao.FilmDao;
import hu.unideb.webdev.repository.entity.ActorEntity;
import hu.unideb.webdev.repository.entity.CategoryEntity;
import hu.unideb.webdev.repository.entity.FilmCategoryEntity;
import hu.unideb.webdev.repository.entity.FilmEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootApplication
public class WebMain implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class,args);
    }

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private FilmActorRepository filmActorRepository;
    @Autowired
    private FilmCategoryRepository filmCategoryRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FilmDao filmDao;

    @Override
    public void run(String... args) throws Exception {
////        System.out.println("Running");
////        System.out.println(Rate.PG13);
//////        StreamSupport.stream(filmRepository.findAll().spliterator(),false)
//////                .limit(1).forEach(filmEntity -> System.out.println(filmEntity));
////
//        ActorEntity actor;
//        actor = ActorEntity.builder()
//                .firstName("Adrian11")
//                .lastName("Adrian2")
//                .lastUpdate(new Timestamp((new Date()).getTime()))
//                .build();
//        CategoryEntity categoryEntity = CategoryEntity.builder().name("Adriad cat")
//                .lastUpdate(new Timestamp((new Date()).getTime())).build();
//        //actorRepository.save(actor);
//        categoryRepository.save(categoryEntity);
//
        //FilmEntity film = filmRepository.findById(1).get();
//        //System.out.println(filmActorRepository.findById(new FilmActorId(214,1)).get().getActor());
//        film.setCategories(filmCategoryRepository.findByFilm(film));
//        //ActorEntity actorEntity = actorRepository.findById(201).get();
//        //System.out.println(actor);
//        //filmActorRepository.delete(filmActorRepository.findByFilmAndActor(film,actorEntity));
//
//        try{
//            film.addCategory(categoryEntity);
//            //film.getActors().forEach(filmActorEntity -> System.out.println(filmActorEntity.getActor()));
//        filmRepository.save(film);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
      //filmActorRepository.findByFilm(film).forEach(filmActorEntity -> System.out.println(filmActorEntity.getActor()));

        long start = System.currentTimeMillis();
        filmDao.readAll().forEach(System.out::println);
        long end = System.currentTimeMillis();
        System.out.println("" +

                (end - start) + "ms");

    }
}
