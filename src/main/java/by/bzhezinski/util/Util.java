package by.bzhezinski.util;

import by.bzhezinski.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Util {
    public static final String animalsDataFileName = "src\\main\\resources\\animals.json";
    public static final String recruitsDataFileName = "src\\main\\resources\\recruits.json";
    public static final String carsDataFileName = "src\\main\\resources\\cars.json";
    public static final String flowersDataFileName = "src\\main\\resources\\flowers.json";
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Animal> getAnimals() throws IOException {
        return newMapper().readValue(new File(animalsDataFileName), new TypeReference<>() {
        });
    }

    public static List<Person> getPersons() throws IOException {
        return newMapper().readValue(new File(recruitsDataFileName), new TypeReference<>() {
        });
    }

    public static List<Car> getCars() throws IOException {
        return newMapper().readValue(new File(carsDataFileName), new TypeReference<>() {
        });
    }

    public static List<Flower> getFlowers() throws IOException {
        return newMapper().readValue(new File(flowersDataFileName), new TypeReference<>() {
        });
    }

    public static List<House> getHouses() throws IOException {
        List<Person> personList = getPersons();
        return List.of(
            new House(1, "Hospital", personList.subList(0, 40)),
            new House(2, "Civil building", personList.subList(41, 141)),
            new House(3, "Civil building", personList.subList(142, 200)),
            new House(4, "Civil building", personList.subList(201, 299)),
            new House(5, "Civil building", personList.subList(300, 399)),
            new House(6, "Civil building", personList.subList(400, 499)),
            new House(7, "Civil building", personList.subList(500, 599)),
            new House(8, "Civil building", personList.subList(600, 699)),
            new House(9, "Civil building", personList.subList(700, 799)),
            new House(9, "Civil building", personList.subList(800, 899)),
            new House(9, "Civil building", personList.subList(900, 999))
        );
    }

    public static List<Department> getDepartment() throws IOException {
        List<Person> personList = getPersons();
        return List.of(
                new Department(1, "Shop1", "production", personList.subList(0, 100)),
                new Department(2, "Shop2", "production", personList.subList(101, 201)),
                new Department(3, "Shop3", "production", personList.subList(202, 302)),
                new Department(4, "Shop4", "production", personList.subList(303, 405)),
                new Department(5, "Shop5", "production", personList.subList(406, 505)),
                new Department(6, "accounting", "itr", personList.subList(506, 515)),
                new Department(7, "commercial", "itr", personList.subList(516, 530)),
                new Department(8, "purchasing", "itr", personList.subList(531, 535)),
                new Department(9, "directorate", "management", personList.subList(536, 540))
        );
    }
    private static ObjectMapper newMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(df);
        mapper.setLocale(Locale.ENGLISH);
        mapper.registerModule(new JSR310Module());
        return mapper;
    }
}
