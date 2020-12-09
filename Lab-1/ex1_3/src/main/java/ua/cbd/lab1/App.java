package ua.cbd.lab1;

import java.util.Set;
import redis.clients.jedis.Jedis;

public class App {
  private Jedis jedis;
  public static String USERS = "users"; // Key set for users' name

  public App() {
    this.jedis = new Jedis("localhost");
  }

  public void saveUser(String username) {
    jedis.sadd(USERS, username);
  }

  public Set<String> getUser() {
    return jedis.smembers(USERS);
  }

  public Set<String> getAllKeys() {
    return jedis.keys("*");
  }

  public static void main(String[] args) {
    App board = new App();
    // set some users
    String[] users = { "Ana", "Pedro", "Maria", "Luis" };
    for (String user : users) board.saveUser(user);
    board.getAllKeys().stream().forEach(System.out::println);
    board.getUser().stream().forEach(System.out::println);
  }
}
