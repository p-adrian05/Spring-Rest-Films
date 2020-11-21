package hu.unideb.webdev;

import hu.unideb.webdev.repository.FilmRepository;
import hu.unideb.webdev.repository.entity.FilmEntity;
import hu.unideb.webdev.repository.util.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.StreamSupport;

@SpringBootApplication
public class WebMain implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class,args);
    }

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running");
        System.out.println(Rate.PG13);
        StreamSupport.stream(filmRepository.findAll().spliterator(),false)
                .limit(1).forEach(filmEntity -> System.out.println(filmEntity));
    }
}
