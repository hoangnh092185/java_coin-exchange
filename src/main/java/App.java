import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    ChangeMachine machine = new ChangeMachine();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/results", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String stringChange  = request.queryParams("change");
      Float floatChange = Float.parseFloat(stringChange);
      String output;
      if (floatChange >8.20){
        output = String.format("Error: Not enough coins.");
      } else {
        output = machine.makeChange(floatChange);
      }

      // Further Exploration: Loop to create all strings and routes.
      // String[] coins = {"Quarter", "Dimes", "Nickels", "Pennies"};
      // for (String coin : coins) {
      //   String (coin)Left = String.format("There are " + machine.get(coin)() + " (coin) left.");
      //   model.put((coin)+"Left", (coin)"Left");
      // }

      String quartersLeft = String.format("There are " + machine.getQuarters() + " quarters left.<br>");
      String dimesLeft = String.format("There are "+ machine.getDimes() + " dimes left.<br>");
      String nickelsLeft = String.format("There are "+ machine.getNickels() + " nickels left.<br>");
      String penniesLeft = String.format("There are "+ machine.getPennies() + " pennies left.");
      model.put("quartersLeft", quartersLeft);
      model.put("dimesLeft", dimesLeft);
      model.put("nickelsLeft", nickelsLeft);
      model.put("penniesLeft", penniesLeft);
      model.put("change", output);
      model.put("template", "templates/results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
