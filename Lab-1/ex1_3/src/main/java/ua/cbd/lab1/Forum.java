package ua.cbd.lab1;

import redis.clients.jedis.Jedis;

public class Forum {
  private Jedis jedis;

  public Forum() {
    this.jedis = new Jedis("localhost");
    System.out.println(jedis.info());
  }

  public static void main(String[] args) {
    new Forum();

    Jedis jedis = new Jedis("localhost");

    jedis.sadd("languages", "Java", "C++", "Python");
    System.out.println("Languages: " + jedis.smembers("languages"));

    jedis.sadd("languages", "Fortran", "Ruby");
    System.out.println("Languages: " + jedis.smembers("languages"));
  }
}
