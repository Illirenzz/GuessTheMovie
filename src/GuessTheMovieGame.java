import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GuessTheMovieGame {
    public static void main(String[] args) {
        List<String> movies = fileStringsToList();

        // загадываем случайную строку
        Random random = new Random();
        String guessTheMovie = movies.get(random.nextInt(movies.size() - 1));
        playGame(guessTheMovie);


    }

    /**
     * читает строки из файла, возвращает эти строки в списке строк,
     * тоже самое, что считать файл в цикле сканнером
     */
    static List<String> fileStringsToList() {
        List<String> result = new ArrayList<>();
        //не знал как сделать, чтобы программа искала путь к фаулу относительно исполняемого файла
        //ответ загуглился за 1 минуту
        String sourceDirectory = System.getProperty("user.dir");
        String filePath = sourceDirectory + File.separator + "src/movies.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.ready()) {
                result.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Метод разбивает переданную строку на символы, и ожидает ввода буквы от пользователя.
     * Также создается массив символов "скрывающих" значение
     * в случае ввода пользователем буквы, которая есть в загаданном слове, всё это слово проходится в цикле
     * и в случае если попадается символ, который ввел пользователь,
     * заменяем соответствующую ячейку зашифрованного слова.
     * проверяем, не отгадал ли пользователь всё слово
     * если угадал то завершаем цикл и поздравляем
     * если не угадал уменьшаем значение попыток и начинаем новый цикл
     *
     * @param movie
     * @return
     */
    static void playGame(String movie) {
        Scanner scanner = new Scanner(System.in);
        boolean guessed = false;

        char[] hiddenWord = "_".repeat(movie.length()).toCharArray();
        char[] movieChars = movie.toCharArray();

        int countOfWrongAnsweredLetters = 0;
        String wrongAnsweredLetters = "";

        for (int i = 10; i > 0; ) {
            System.out.println("Вы угадываете: " + String.copyValueOf(hiddenWord));
            System.out.println("Вы ошиблись (" + countOfWrongAnsweredLetters +
                    ") раз, неверные буквы: " + wrongAnsweredLetters.trim());
            System.out.println("Угадайте букву:");
            char aChar = scanner.nextLine().charAt(0);

            if (movie.contains("" + aChar)) {
                for (int j = 0; j < movie.length(); j++) {
                    if (movieChars[j] == aChar) {
                        hiddenWord[j] = aChar;
                    }
                }

                if (String.copyValueOf(hiddenWord).equals(movie)) {
                    guessed = true;
                    break;
                }

            } else {
                countOfWrongAnsweredLetters++;
                wrongAnsweredLetters = wrongAnsweredLetters + aChar + " ";
                System.out.println("Осталось попыток: " + --i);
            }
        }

        if (guessed) {
            System.out.println("Поздравляю, вы угадали! Загаданное слово - " + movie);
        } else {
            System.out.println("Получится в следующий раз!");
        }
    }
}
