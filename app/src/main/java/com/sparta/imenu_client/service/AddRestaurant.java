package com.sparta.imenu_client.service;

import android.os.AsyncTask;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Menu;
import com.sparta.imenu_client.model.Restaurant;
import com.sparta.imenu_client.model.Section;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hamed on 4/1/16.
 */
public class AddRestaurant extends AsyncTask<Void,Void,String> {
    @Override
    protected String doInBackground(Void... params) {
        RestTemplate restTemplate=  new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Restaurant restaurant= new Restaurant("KFC","Fast food","KFC Cairo",
                "https://media.glassdoor.com/sql/559399/kfc-holdings-squarelogo-1428385037396.png",
                null,null);
<<<<<<< HEAD
        Menu menu = new Menu();
=======
        Menu menu = new Menu(null);
>>>>>>> 4f91d9b43b73f18457ed993135ca27bea0d50aeb
        String names[] = {"Mighty Bucket Meal","Mighty Popcorn Meal","My Meal","Super Snack Meal","Super Snack Meal",
        "Dinner Box","Dinner Box","Extra Chicken Piece"};
        String urls[]={"https://assets.otlob.com/dynamic/images/products/62/62574_1453380791_ma.png",
        "https://assets.otlob.com/dynamic/images/products/62/62579_1453380790_ma.png",
        "https://assets.otlob.com/dynamic/images/products/62/62580_1453387656_ma.jpg",
        "https://assets.otlob.com/dynamic/images/products/62/62587_1453387656_ma.jpg",
        "https://assets.otlob.com/dynamic/images/products/62/62588_1442482035_ma.jpg",
        "https://assets.otlob.com/dynamic/images/products/62/62612_1448372552_ma.jpg",
        "https://assets.otlob.com/dynamic/images/products/62/62613_1448372552_ma.jpg",
        "https://assets.otlob.com/dynamic/images/products/62/62614_1453381076_ma.jpg",
        };
        double prices[] ={48.18,50.00,10.45,23.64,18.64,31.82,39.54,9.54};
        String descriptions[]={"2 pieces chicken + 3 pieces chicken strips + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",
        "2 pieces chicken + 2 pieces chicken strips + 10 popcorn + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",
        "one chicken piece, served with small French fries + 2 bun bread",
        "2 pieces chicken, served with large rice + small French fries + bun",
        "2 pieces fried chicken + small French fries + bun",
        "3 pieces fried chicken + small French fries + coleslaw salad + bun",
        "3 pieces fried chicken + medium French fries + coleslaw salad + bun",
<<<<<<< HEAD
        "a"};
=======
        ""};
>>>>>>> 4f91d9b43b73f18457ed993135ca27bea0d50aeb
        ArrayList<String> keywords[] = new ArrayList[]{new ArrayList<String>(Arrays.asList("chicken","strips","fries",
                "coleslaw","salad","bun","garlic mayonnaise sauce","dynamite sauce","drink","bread" )),
                new ArrayList<String>(Arrays.asList("chicken","strips","popcorn","fries","coleslaw","bun","garlic mayonnaise sauce","dynamite sauce","drink","bread")),
                new ArrayList<String>(Arrays.asList("chicken","one piece","fries","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","2 pieces","rice","large rice","fries","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","2 pieces","fries","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","3 pieces","fries","small fries","coleslaw","salad","bun","bread")),
<<<<<<< HEAD
                new ArrayList<String>(Arrays.asList("chicken","3 pieces","fries","small fries","coleslaw","salad","bun","bread")),
=======
>>>>>>> 4f91d9b43b73f18457ed993135ca27bea0d50aeb
                new ArrayList<String>(Arrays.asList("chicken","3 pieces","fries","medium fries","coleslaw","salad","bun","bread"))
        };
        ArrayList<Item> items = new ArrayList<Item>();
        for (int i=0;i<names.length;i++) {
            items.add(new Item(names[i],"KFC", "chicken", prices[i], descriptions[i], keywords[i], urls[i]));
        }
        Section section = new Section(items,0);
        menu.addSection(section);
        String names2[] = {" Crispy Strips Light Meal","Crispy Strips Meal","Crispy Strips Meal","Crispy Strips Piece"};
        String urls2[] = {"https://assets.otlob.com/dynamic/images/products/62/62863_1453381191_ma.png",
        "https://assets.otlob.com/dynamic/images/products/62/62864_1453381190_ma.png",
        "https://assets.otlob.com/dynamic/images/products/62/62865_1453381352_ma.png",
        "https://assets.otlob.com/dynamic/images/products/62/62866_1453383366_ma.png"};
        double prices2[] ={18.64,31.82,39.54,6.82};
        String descriptions2 []={"3 pieces crispy strips + small French fries + bun",
        "5 pieces crispy strips + small French fries + coleslaw salad + bun",
        "5 pieces crispy strips + medium French fries + coleslaw salad + bun + soft drink", null};
        ArrayList<String> keyworkds2[] = new ArrayList[]{ new ArrayList<String>(Arrays.asList("chicken","strips","crispy",
                "crispy strips","fries","small fries","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","strips","crispy","fries","coleslaw","bun","small fries","5 pieces","bread")),
                new ArrayList<String>(Arrays.asList("chicken","5 pieces","crispy","bun","bread","fries","small fries")),
                null,
        };
        ArrayList<Item> items2 = new ArrayList<Item>();
        Section section2 = new Section(items2,0);
        menu.addSection(section2);
        restaurant.addMenu(menu);
<<<<<<< HEAD
        String response = restTemplate.postForObject("http://lambada.eu-central-1.elasticbeanstalk.com/ "+"restaurant/add",restaurant,String.class);
=======
        String response = restTemplate.postForObject(R.string.url+"restaurant/add",restaurant,String.class);
>>>>>>> 4f91d9b43b73f18457ed993135ca27bea0d50aeb
        return response;
    }
}
