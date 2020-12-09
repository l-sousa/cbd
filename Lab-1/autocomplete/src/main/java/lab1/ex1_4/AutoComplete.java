package lab1.ex1_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class AutoComplete {
    private static Jedis jedis;

    public AutoComplete() {
        jedis = new Jedis("localhost");
        jedis.flushAll();
    }

    public static void GenerateData(String file) {
        try {
            File names = new File(file);
            Scanner fileReader = new Scanner(names);
            Transaction t = jedis.multi();

            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                t.zadd("female_names", 0, data); // adiciona ao set ordenado 'female_names' a nova pessoa
            }
            t.exec();
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String[] MinMaxWord(String word) {
        String min = word;
        String noLastChar = word.substring(0, word.length() - 1); // remove o ultimo char da word
        char newLastChar = word.substring(word.length() - 1).charAt(0);
        newLastChar++; // aumenta o char antigo em 1 letra
        String max = noLastChar + newLastChar;
        String[] res = { min, max };
        return res;

        /*
         * exemplo: 'zora' fica zor + b => zorb && isto permite que a funçao zrangeByLex
         * vá buscar todos os valores comprimidos entre a palavra inicial 'zora' e a
         * última palavra possível no range lexicografico -> que é 'zorb'
         */
    }

    private static void SearchResults(String input) {
        String[] min_max = MinMaxWord(input);
        jedis.zrangeByLex("female_names", "[" + min_max[0], "[" + min_max[1]).stream().forEach(System.out::println);

    }

    public static void main(String[] args) {
        AutoComplete a = new AutoComplete();
        Scanner sc = new Scanner(System.in);
        GenerateData("female-names.txt");

        String input;
        while (true) {
            System.out.print("\n Search for ('Enter' for quit): ");

            input = sc.nextLine();

            if (input.equals("")) {
                break;
            }

            SearchResults(input);

        }
        sc.close();
        jedis.close();
    }

}
