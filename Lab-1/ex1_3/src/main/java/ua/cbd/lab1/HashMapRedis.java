package ua.cbd.lab1;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class HashMapRedis {
  private Jedis jedis;
  public static String USERS = "users"; // Key set for users' name

  public HashMapRedis() {
    this.jedis = new Jedis("localhost");
  }

  public void saveUser(String username, String count) {
    jedis.hset(USERS, username, count);
  }

  public Map<String, String> getUser() {
    return jedis.hgetAll(USERS);
  }

  public Set<String> getAllKeys() {
    return jedis.keys("*");
  }

  public static void main(String[] args) {
    HashMapRedis board = new HashMapRedis();
    // set some users
    String[] users = { "Ana", "Pedro", "Maria", "Luis" };
    int count = 0;
    for (String user : users) {
      board.saveUser(user, "" + count);
      count++;
    }
    board.getAllKeys().stream().forEach(System.out::println);
    board.getUser().entrySet().stream().forEach(System.out::println);
  }
}
