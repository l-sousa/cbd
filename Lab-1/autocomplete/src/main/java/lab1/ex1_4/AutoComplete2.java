package lab1.ex1_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class AutoComplete2 {

    private static Jedis jedis;

    public AutoComplete2() {
        jedis = new Jedis("localhost");
        jedis.flushAll();
    }

    public static void GenerateData(String file) {
        try {
            File names = new File(file);
            Scanner fileReader = new Scanner(names);
            Transaction t = jedis.multi();

            while (fileReader.hasNextLine()) {
                String[] data = fileReader.nextLine().split(";");
                t.zadd("names_withscore", Integer.parseInt(data[2]), data[0]);
                t.zadd("names_noscore", 0, data[0]);

            }
            t.exec();
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String[] GetMinMaxWord(String word) {
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
        String[] min_max = GetMinMaxWord(input);

        // vai a todos os nomes com score e dá print dos que estão aptos
        jedis.zrevrangeByScore("names_withscore", "+inf", "-inf").stream().forEach(name -> {
            if (jedis.zrangeByLex("names_noscore", "[" + min_max[0], "[" + min_max[1]).contains(name)) {
                System.out.println(name + " " + jedis.zscore("names_withscore", name));
            }
        });

    }

    public static void main(String[] args) {
        AutoComplete2 a = new AutoComplete2();
        Scanner sc = new Scanner(System.in);
        GenerateData("nomes-registados-2020.csv");

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
