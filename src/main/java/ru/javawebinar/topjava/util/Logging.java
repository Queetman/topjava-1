package main.java.ru.javawebinar.topjava.util;

import main.java.ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Макс on 23.01.2018.
 */
public class Logging {

    public Logging() {
    }


    public static void printData(List<UserMealWithExceed> mealList) {

        Logger logger = Logger.getLogger("MealsInfo");


        for (UserMealWithExceed meal :
                mealList) {

            if (!mealList.isEmpty()) {

                logger.log(Level.INFO, "Time: " + meal.getDateTime() + "\n" +
                        "Calories: " + meal.getCalories() + "\n" +
                        "Description:" + meal.getDescription() + "\n" +
                        "Exceed:" + meal.isExceed());

            } else logger.log(Level.INFO, "no meals");

        }


    }

}
