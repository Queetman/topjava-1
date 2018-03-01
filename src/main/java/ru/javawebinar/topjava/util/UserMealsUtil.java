package main.java.ru.javawebinar.topjava.util;

import main.java.ru.javawebinar.topjava.model.UserMeal;
import main.java.ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GKinmaislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 5000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 5100)

        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        // printList(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        Logging.printData(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

    }

    //есть mealList. Необходимо вернуть лист, содержащий данные о приеме пищи между
// startTime и EndTime  с Exceed (булевское значение определяющее уложился ли человек по каллориями между этими приемами писчи)
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> initMealList, LocalTime startTime, LocalTime endTime, int caloriesRate) {

        //Тут создан еще лист и добавлено еще одно значение. Почему добавено-см. ниже
        List<UserMeal> mealList = new ArrayList<>();
        mealList.addAll(initMealList);
        mealList.add(new UserMeal(LocalDateTime.of(17, Month.DECEMBER, 31, 23, 59), "Ужин диетический", 0)
        );
        List<UserMealWithExceed> mealListWithExceed = new ArrayList<>();

        //Сначала необходимо разобраться с Exceed. Необходимо создать лист,
        // который будет показывать превышен ли объем каллорий или нет.
        ArrayList<Boolean> exceed = new ArrayList<>();

//начвльная дата для обработки
        LocalDate initDate = mealList.get(0).getDateTime().toLocalDate();

        int calloriesPerDay = 0;
        int mealsPerDay = 0;
        boolean exceedAtDay;


        for (UserMeal aMealList : mealList) {

            LocalDate currentDate = aMealList.getDateTime().toLocalDate();
//Есои даты одинаковые, т.е день один и тот же, тогда идет подсчет количества
            //каллорий и приемов пищи

            if (currentDate.equals(initDate)) {
                calloriesPerDay += aMealList.getCalories();
                mealsPerDay++;

            }

            //если даты разные, тогда заполняется массив exceed и
            // обновляется счетчик каллорий и приемов пищи.

            //зачем добавлялся дополнительный элемент массива: когда i=mealListSize
            // и если текущая дата равна начвльной дате, тогда условие if
            // выполняется, а else нет и не происходит добавление данных
            //в массив exceed.  Поэтому нужно добавление последнего значения, которое
            //по дате гарантированно не будет совпадать с предыдущим.
            //Если в initMealList  проссто добавить значение, возникает эксэпшн.
            //с чего- не понятно. Можно было кпростить.
            else {

                //определение превышения нормы калорий в день
                if (calloriesPerDay > caloriesRate) exceedAtDay = true;
                else exceedAtDay = false;

                //заполнение булевского массива
                for (int j = 0; j < mealsPerDay; j++) {
                    exceed.add(exceedAtDay);
                }
                //обновление переменных
                mealsPerDay = 1;
                calloriesPerDay = aMealList.getCalories();
                initDate = currentDate;
            }
        }


        //Далее необходимо заполить конечный лист. Для этого сначала нужео проверить,
        //находится ли прием пищи между заданным

        //exceed size=3 отсюда и хрень см выше
        for (int i = 0; i < mealList.size(); i++) {

            //сначала ижет проверка, вписывается ом данный прием пищи во временные рамки
            if (TimeUtil.isBetween(mealList.get(i).getDateTime().toLocalTime(), startTime, endTime)) {
                LocalDateTime time = mealList.get(i).getDateTime();
                int calories = mealList.get(i).getCalories();
                String description = mealList.get(i).getDescription();

                mealListWithExceed.add(new UserMealWithExceed(time, description, calories, exceed.get(i)));

            }


        }

        // TODO return filtered list with correctly exceeded field

        return mealListWithExceed;
    }


}
