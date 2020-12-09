package lab1.ex1_5;

import static java.lang.System.out;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import redis.clients.jedis.Jedis;

public class App {
  private static Jedis jedis;
  private static Scanner sc = new Scanner(System.in);

  public App() {
    jedis = new Jedis("localhost");
    jedis.flushAll();
  }

  private static boolean isValidInput(String input) {
    String[] args = input.split(" ");
    switch (args[0]) {
      case "add":
        if (args.length < 2)
          return false;
        else
          return true;

      case "add_info":
        if (args.length < 2)
          return false;
        else
          return true;

      case "info":
        if (args.length < 2)
          return false;
        else
          return true;

      case "list_all":
        return true;

      case "rm":
        if (args.length < 2)
          return false;
        else
          return true;

      case "dm":
        if (args.length < 4)
          return false;
        else
          return true;

      case "read_dm":
        if (args.length < 3)
          return false;
        else
          return true;

      case "msg":
        if (args.length < 3)
          return false;
        else
          return true;

      case "follow":
        if (args.length < 3)
          return false;
        else
          return true;

      case "by":
        if (args.length < 2)
          return false;
        else
          return true;

      case "feed":
        if (args.length < 2)
          return false;
        else
          return true;

      case "quit":
        return true;

      default:
        return false;
    }
  }

  public static void ShowMenu() {
    out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
    out.println("<><><><><><><><><><><><><><><><><><><>  MENU  <><><><><><><><><><><><><><><><><><>");
    out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
    out.println("<> add       <user>                      (add user to system)");
    out.println("<> rm        <user>                      (remove user from system)");
    out.println("<> add_info  <user>                      (add extra info about user)");
    out.println("<> list_all                              (list every user in the system)");
    out.println("<> msg       <user> \"<msg>\"             (send messagem to system)");
    out.println("<> dm        <user1> <user2> \"<msg>\"     (user1 send private msg to 2)");
    out.println("<> read_dm   <user1> <user2>             (read private msg from user1 to user2)");
    out.println("<> follow    <user1> <user2>             (user1 follow user2)");
    out.println("<> by        <user>                      (see messages by a user");
    out.println("<> feed      <user>                      (see messages from followed users)");
    out.println("<> info      <user>                      (see extra info about user)");
    out.println("<> quit");
    out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
    out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
    out.println();
  }

  public static void add(String[] args) {
    if (!jedis.exists("messages/" + args[1]))
      jedis.lpush("messages/" + args[1], "");
    else
      out.println("User already exists!");
  }

  public static void msg(String[] args) {
    String[] n_args = args[0].split(" ");

    if (jedis.exists("messages/" + n_args[1])) {
      jedis.lpush("messages/" + n_args[1], args[1]);
    } else
      out.println("User doesn't exist!");
  }

  public static void follow(String[] args) {
    if (jedis.exists("messages/" + args[1]) && jedis.exists("messages/" + args[2]))
      // adicionar novo user à lista
      jedis.lpush("following/" + args[1], args[2]);
    else
      out.println("User doesn't exist!");
  }

  public static void by(String[] args) {
    if (jedis.exists("messages/" + args[1])) {
      out.println("<><><><><><><><><><><><><><>");
      out.println("By " + args[1]);
      out.println("<><><><><><><><><><><><><><>");
      // print de todas as mensagens de um user
      jedis.lrange("messages/" + args[1], 0, -1).forEach(msg -> {
        if (!msg.equals(""))
          out.println(msg);
      });
    } else
      out.println("User doesn't exist!");
    out.println("<><><><><><><><><><><><><><>\n");

  }

  public static void feed(String[] args) {

    if (jedis.exists("following/" + args[1])) {
      // para cada user que o user segue
      jedis.lrange("following/" + args[1], 0, -1).forEach(followed -> {
        if (jedis.exists("messages/" + followed)) {
          out.println("<><><><><><><><><><><><><><>");
          out.println("By " + followed);
          out.println("<><><><><><><><><><><><><><>");
          // print das mensagens de cada um
          jedis.lrange("messages/" + followed, 0, -1).forEach(msg -> {
            if (!msg.equals(""))
              out.println(msg);
          });
          out.println("<><><><><><><><><><><><><><>\n");
        }
      });
    } else
      out.println("User doesn't follow anyone yet!");
  }

  public static boolean Execute(String input) {
    String[] args = input.split(" ");

    switch (args[0]) {
      case "add":
        add(args);
        return true;

      case "add_info":
        add_info(args);
        return true;

      case "info":
        info(args);
        return true;

      case "list_all":
        list_all();
        return true;

      case "rm":
        rm(args);
        return true;

      case "dm":
        dm(input.split("\""));
        return true;

      case "read_dm":
        read_dm(args);
        return true;

      case "msg":
        msg(input.split("\""));
        return true;

      case "follow":
        follow(args);
        return true;

      case "by":
        by(args);
        return true;

      case "feed":
        feed(args);
        return true;

      case "quit":
        return false;

      default:
        return false;
    }
  }

  public static void add_info(String[] args) {
    if (jedis.exists("messages/" + args[1])) {

      out.print("Age > ");
      String age = sc.nextLine();
      while (!NumberUtils.isParsable(age)) {
        out.println("Invalid age! ");
        out.print("Age > ");
        age = sc.nextLine();
      }

      out.print("Bio (custom status message) > ");
      String bio = sc.nextLine();

      jedis.hset("info/" + args[1], "age", age);
      jedis.hset("info/" + args[1], "bio", bio);

    } else
      out.println("User doesn't exist!");

  }

  public static void info(String[] args) {
    if (jedis.exists("info/" + args[1])) {
      out.println("<><><><><><><><><><><><><><>");
      out.println("Info of " + args[1]);
      out.println("<><><><><><><><><><><><><><>");
      out.println("Age: " + jedis.hget("info/" + args[1], "age"));
      out.println("Bio: " + jedis.hget("info/" + args[1], "bio"));
      out.println("<><><><><><><><><><><><><><>\n");

    } else
      out.println("No info available!");

  }

  public static void list_all() {
    jedis.keys("messages/*").stream().forEach(user -> {
      out.println("> " + user.replace("messages/", ""));
    });
  }

  public static void rm(String[] args) {
    jedis.keys("*").stream().forEach(user -> {
      String current = user.split("/")[1];
      // && !current.contains("&") porque no caso das dms é diferente
      if (current.equals(args[1]) && !current.contains(":")) {
        jedis.del(user);
      }

      if (current.contains(":")) {
        // formato de dms >>> dm/user1:user2
        String user1 = current.split(":")[0];
        String user2 = current.split(":")[1];
        if (user1.equals(args[1]))
          jedis.del(user);
        if (user2.equals(args[1]))
          jedis.del(user);
      }
    });

  }

  public static void dm(String[] args) {
    String[] n_args = args[0].split(" ");

    if (jedis.exists("messages/" + n_args[1]) && jedis.exists("messages/" + n_args[2]))
      // adicionar novo user à lista
      jedis.lpush("dm/" + n_args[1] + ":" + n_args[2], n_args[1] + " > " + args[1]);
    else
      out.println("User doesn't exist!");
  }

  public static void read_dm(String[] args) {
    if (jedis.exists("dm/" + args[1] + ":" + args[2])) {
      out.println("<><><><><><><><><><><><><><>");
      out.println("To " + args[2]);
      out.println("<><><><><><><><><><><><><><>");
      jedis.lrange("dm/" + args[1] + ":" + args[2], 0, -1).forEach(msg -> {
        out.println(msg);
      });
    } else
      out.format("No dms yet!");
    out.println("<><><><><><><><><><><><><><>\n");

  }

  public static void main(String[] args) {
    new App();

    ShowMenu();
    String input;

    // Main loop
    while (true) {
      out.print(">>> ");
      input = sc.nextLine();

      // Validate Input
      if (!isValidInput(input)) {
        out.println("Invalid Command!");
        continue;
      }

      // Execute given command
      if (!Execute(input)) {
        break;
      }
    }
    sc.close();
  }
}
