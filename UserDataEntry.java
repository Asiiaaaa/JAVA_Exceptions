import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserDataEntry {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, BufferedWriter> fileWriters = new HashMap<>();

        System.out.println("Введите данные в формате: Фамилия Имя Отчество Дата_рождения Номер_телефона Пол");

        try {
            String[] userData = scanner.nextLine().split(" ");

            if (userData.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных");
            }

            String surname = userData[0];
            String name = userData[1];
            String patronymic = userData[2];
            String birthDate = userData[3];
            long phoneNumber = Long.parseLong(userData[4]);
            char gender = userData[5].charAt(0);

            if (gender != 'f' && gender != 'm') {
                throw new IllegalArgumentException("Неверный формат пола");
            }

            BufferedWriter writer = fileWriters.get(surname);
            if (writer == null) {
                writer = new BufferedWriter(new FileWriter(surname + ".txt", true)); // true указывает на доступ на дозапись в файл
                fileWriters.put(surname, writer);
            }

            writer.write(surname + " " + name + " " + patronymic + " " + birthDate + " " + phoneNumber + " " + gender);
            writer.newLine();

        } catch (NumberFormatException e) {
            System.err.println("Неверный формат данных. Номер телефона должен быть целым числом");
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            for (BufferedWriter writer : fileWriters.values()) {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии файла: " + e.getMessage());
                }
            }
        }
    }
}
