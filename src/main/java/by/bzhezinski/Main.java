package by.bzhezinski;

import by.bzhezinski.model.*;
import by.bzhezinski.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
    }


    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int[] counter = {0};
        System.out.println(animals.stream().filter(it -> it.getAge() >= 10 && it.getAge() < 20)
                .sorted((an1, an2) -> an1.getAge() - an2.getAge())
                .collect(Collectors.groupingBy(animal -> {
                    int i = counter[0] / 7;
                    counter[0]++;
                    return "Zoo" + (i + 1);
                })).get("Zoo3"));
        //..sorted((an1, an2) -> an1.getAge() - an2.getAge()).skip(14).limit(7);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(an -> "Japanese".equals(an.getOrigin()))
                .peek(an -> an.setBread(an.getBread().toUpperCase())).filter(an -> "Female".equals(an.getGender()))
                .map(Animal::getBread).forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(it -> it.getAge() > 30).map(Animal::getOrigin).filter(it -> it.startsWith("A"))
                .distinct().forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().filter(it -> "Female".equals(it.getGender())).count());

    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().filter(it -> it.getAge() >= 20 && it.getAge() <= 30)
                .anyMatch(it -> "Hungarian".equals(it.getOrigin())) ? "yes" : "no");
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .allMatch(it -> "Male".equals(it.getGender()) || "Female".equals(it.getGender())) ? "yes" : "no");
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .noneMatch(it -> "Oceania".equals(it.getOrigin())) ? "7. no from Oceania" : "7. there is from Oceania");
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream().sorted((an1, an2) -> an1.getGender().compareToIgnoreCase(an2.getGender())).limit(100)
                .max((an1, an2) -> an1.getAge() - an2.getAge())
                .ifPresentOrElse(an -> System.out.println("8.The oldest animal is : " + an), () -> System.out.println("There isn't animal"));
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println("9. The shortest array has length - " + animals.stream().map(Animal::getBread).map(String::toCharArray)
                .min(Comparator.comparingInt(ar -> ar.length)).get().length);
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println("10.The total age of all animals is - " + animals.stream()
                .collect(Collectors.summarizingInt(Animal::getAge)).getSum());

        // animals.stream().mapToInt(Animal::getAge).sum();
        // animals.stream().mapToInt(Animal::getAge).reduce((a,b)->a+b).getAsInt();
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println("11.The average age of the animal from Indonesian is - " + animals.stream()
                .filter(an -> "Indonesian".equals(an.getOrigin()))
                .collect(Collectors.averagingInt(Animal::getAge)));
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        LocalDate now = LocalDate.now();
        people.stream().filter(person -> "Male".equals(person.getGender()))
                .filter(person -> {
                    int age = Period.between(person.getDateOfBirth(), now).getYears();
                    return age >= 18 && age < 27;
                })
                .sorted((p1, p2) -> p1.getRecruitmentGroup() - p2.getRecruitmentGroup())
                .limit(200).forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        LocalDate now = LocalDate.now();
        Stream<Person> stream1 = houses.stream().filter(h -> "Hospital".equals(h.getBuildingType()))
                .flatMap(h -> h.getPersonList().stream());
        Stream<Person> stream2 = houses.stream().filter(h -> !"Hospital".equals(h.getBuildingType()))
                .flatMap(h -> h.getPersonList().stream())
                .filter(person -> {
                    int age = Period.between(person.getDateOfBirth(), now).getYears();
                    return age < 18 || (age >= 63 && "Male".equals(person.getGender())) || (age >= 58 && "Female".equals(person.getGender()));
                });
        Stream.concat(stream1, stream2).limit(500).forEach(System.out::println);
    }

    private static void task14() throws IOException {
        final int rate = 714;  // in cent
        final String others = "others";
        List<Car> cars = Util.getCars();
        List<Function<Car, String>> classifiers = Arrays.asList(Main::carForTurkmenistanTest,
                Main::carForUzbekistanTest, Main::carForKazakhstanTest, Main::carForKyrgyzstanTest,
                Main::carForRussiaTest, Main::carForMongoliaTest);
        long[] totalValue = {0};

        cars.stream().collect(Collectors.groupingBy(car -> classifiers.stream()
                                .map(ob -> ob.apply(car)).filter(Objects::nonNull).findFirst().orElse(others),
                        Collectors.summarizingLong(Car::getMass)))
                .forEach((k, v) -> {
                    if (!others.equals(k)) {
                        long costPerCountry = (long) (v.getSum() / 1000.0 * rate); // in cent
                        System.out.printf(" %s : total weight = %.3f тонн, total cost = %.2f $\n",
                                k, v.getSum() / 1000.0, costPerCountry / 100.0);
                        totalValue[0] += costPerCountry;
                    }
                });
        System.out.printf("Total value of delivery =  %.2f\n", totalValue[0] / 100.0);

        /* Если нужен список авто для каждой страны
        cars.stream().collect(Collectors.groupingBy(car -> {
            for (Function<Car, String> classifier : classifiers) {
                String key = classifier.apply(car);
                if (key != null) return key;
            }
            return others})).forEach((k, v) -> {
                    if (!others.equals(k)) {
                        long totalWeight = v.stream().collect(Collectors.summarizingLong(Car::getMass)).getSum();
                        long costPerCountry = (long) (totalWeight / 1000.0 * rate); // in cent
                        System.out.printf(" %s : total weight = %.3f тонн, total cost = %.2f $\n",
                                k, totalWeight / 1000.0, costPerCountry / 100.0);
                        totalValue[0] += costPerCountry;
                    }
                });*/
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        long totalSum = flowers.stream().sorted(Comparator.comparing(Flower::getOrigin, (o1, o2) -> o2.compareTo(o1))
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay, (c1, c2) -> (int) Math.floor(c2 - c1)))
                .filter(f -> f.getCommonName().charAt(0) <= 'S').filter(f -> f.getCommonName().charAt(0) > 'C')
                .filter(Flower::isShadePreferred).filter(f -> f.getFlowerVaseMaterial().stream()
                        .anyMatch(it -> "Steel".equals(it) || "Aluminum".equals(it) || "Glass".equals(it)))
                .mapToLong(f -> f.getPrice() + Math.round(f.getWaterConsumptionPerDay() * 365 * 5 / 1000 * 139))
                .sum();
        System.out.printf("Total expenses is %.2f $", totalSum / 100.0);
    }

    private static void task16() throws IOException {
        List<Department> departments = Util.getDepartment();
        LocalDate now = LocalDate.now();
        Map<String, List<Person>> employee;
        departments.stream().filter(d -> "production".equals(d.getDepartmentType()))
                .flatMap(d -> d.getPersonList().stream())
                .filter(person -> {
                    int age = Period.between(person.getDateOfBirth(), now).getYears();
                    return (age == 62 && "Male".equals(person.getGender())) || (age == 57 && "Female".equals(person.getGender()));
                }).sorted(Comparator.comparing(Person::getDateOfBirth)).collect(Collectors.groupingBy(Person::getGender))
                .forEach((k, v) -> {
                    System.out.println(k + " , quantity - " + v.size());
                    v.stream().forEach(System.out::println);
                });
    }

    private static String carForTurkmenistanTest(Car car) {
        return "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor()) ? "Туркменистан" : null;
    }

    private static String carForUzbekistanTest(Car car) {
        return (car.getMass() < 1500 || "BMW".equals(car.getCarMake()) || "Lexus".equals(car.getCarMake())
                || "Chrysler".equals(car.getCarMake()) || "Toyota".equals(car.getCarMake())) ? "Узбекистан" : null;
    }

    private static String carForKazakhstanTest(Car car) {
        return ((car.getMass() > 4000 && "Black".equals(car.getColor()))
                || "GMC".equals(car.getCarMake()) || "Dodge".equals(car.getCarMake())) ? "Казахстан" : null;
    }

    private static String carForKyrgyzstanTest(Car car) {
        return (car.getReleaseYear() < 1982 || "Civic".equals(car.getCarModel())
                || "Cherokee".equals(car.getCarModel())) ? "Кыргызстан" : null;
    }

    private static String carForRussiaTest(Car car) {
        return (car.getPrice() > 40000 || !("Yellow".equals(car.getColor()) || "Red".equals(car.getColor())
                || "Green".equals(car.getColor()) || "Blue".equals(car.getColor()))) ? "Россия" : null;
    }

    private static String carForMongoliaTest(Car car) {
        return (car.getVin().contains("59".toString())) ? "Монголия" : null;
    }
}