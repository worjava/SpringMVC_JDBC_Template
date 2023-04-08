package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import web.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component // спринг создаст бин этого класса
public class PersonDAO { // будет общаться с БД работать со списком людей
    private static int PEOPLE_COUNT;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index()  { // return all people
        return jdbcTemplate.query("SELECT * from person", new BeanPropertyRowMapper<>(Person.class)); // в качетсве параметра маапе
// возвращаем весь список таблицы персон
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id =?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }//С помощью jdbcTemplate выполняется запрос к базе данных на выборку всех полей для записи, у которой идентификатор равен переданному id.

    public void save(Person person) {                                       //1?
       jdbcTemplate.update("INSERT INTO Person (name, age, email) VALUES (?, ?, ?)",person.getName(),
               person.getAge(),person.getEmail());

    }


    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?,age=?,email=? WHERE id=?",  person.getName(),
                person.getAge(),person.getEmail(),id);
    }


    public void delete(int id) {
     jdbcTemplate.update("DELETE FROM Person WHERE id=?",id);
    }

//
//        people.removeIf(a -> a.getId() == id);
}
