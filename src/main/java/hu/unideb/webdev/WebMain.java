package hu.unideb.webdev;

import hu.unideb.webdev.Model.Actor;
import hu.unideb.webdev.Model.Category;
import hu.unideb.webdev.repository.*;
import hu.unideb.webdev.repository.dao.ActorDao;
import hu.unideb.webdev.repository.dao.CategoryDao;
import hu.unideb.webdev.repository.dao.FilmDao;
import hu.unideb.webdev.service.ActorService;
import hu.unideb.webdev.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
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
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private FilmService filmService;
    @Autowired
    private ActorService actorService;

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

        //long start = System.currentTimeMillis();
      // filmDao.readAll().forEach(System.out::println);
      //  long end = System.currentTimeMillis();
      //  System.out.println("" +
//            //    (end - start) + "ms");
//       Film film = Film.builder().description("Desc")
//               .language("hungary")
//               .originalLanguage("english")
//               .length(23)
//               .rating(Rate.NC17)
//               .releaseYear(2006)
//               .rentalDuration(13)
//               .rentalRate(23.4)
//               .replacementCost(23.3)
//               .title("Film actornak")
//               .specialFeatures(List.of(SpecialFeature.DELETED_SCENES))
//               .categories(List.of("Action"))
//               .build();
     // Film film2 =filmDao.getFilmById(1000);
//       Actor actor = Actor.builder()
//               .id(1)
//               .firstName("Adrian")
//               .lastName("Petrasko").build();
//       actorDao.createActor(actor);
//       filmDao.createFilm(film);
        //Film film = filmDao.getFilmById(1039);
        //filmDao.readAll().forEach(System.out::println);
        //film.setTitle("Magyar film update3");
        //film.getCategories().add("New");
        //filmDao.updateFilm(film);
//        long start = System.currentTimeMillis();
//         categoryDao.readAll().forEach(System.out::println);
//          long end = System.currentTimeMillis();
//          System.out.println("" + (end - start) + "ms");
//        Actor actor = actorDao.getActorById(217);
//        //actor.setFilms(new LinkedList<>());
//        actor.setFirstName(null);
//        actorDao.updateActor(actor);
       //Category category=  Category.builder().name("OwnCategory").build(); for(String categoryName : newCategoryNames){
        //            filmCategoryRepository
        //                    .save(new FilmCategoryEntity(queryCategory(categoryName),
        //                            filmEntity,new Timestamp((new Date()).getTime())));
        //        }
        //categoryDao.deleteCategory(categoryDao.getCategoryById(19));
         long start = System.currentTimeMillis();
         actorService.getActorsInFilm(filmDao.getFilmById(1)).forEach(System.out::println);
        //filmService.getFilmsInCategory("Action").forEach(System.out::println);
          long end = System.currentTimeMillis();
          System.out.println("" + (end - start) + "ms");
    }
}
