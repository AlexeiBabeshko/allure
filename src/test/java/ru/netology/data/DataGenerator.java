package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataGenerator {
    static final String dateFormat = "dd.MM.yyyy";

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String generateCity(String locale) throws IOException {
        // генерация города
        Faker faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateName(String locale) {
        // генерация фамилия + имя
        Faker faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        // генерация номера телефона
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) throws IOException {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
