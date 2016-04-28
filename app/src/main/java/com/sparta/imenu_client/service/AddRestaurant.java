package com.sparta.imenu_client.service;

import android.os.AsyncTask;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.LoginActivity;
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
    LoginActivity context;

    public AddRestaurant(LoginActivity context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        RestTemplate restTemplate=  new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Restaurant restaurant= new Restaurant("KFC","Fast food","KFC Cairo",
                "https://marketing.otlob.com/images/nl/otlob/kfcL.png",
                null,null);
        Menu menu = new Menu();
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
        };        double prices[] ={48.18,50.00,10.45,23.64,18.64,31.82,39.54,9.54};
        String descriptions[]={"2 pieces chicken + 3 pieces chicken strips + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",
                "2 pieces chicken + 2 pieces chicken strips + 10 popcorn + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",
                "one chicken piece, served with small French fries + 2 bun bread",
                "2 pieces chicken, served with large rice + small French fries + bun",
                "2 pieces fried chicken + small French fries + bun",
                "3 pieces fried chicken + small French fries + coleslaw salad + bun",
                "3 pieces fried chicken + medium French fries + coleslaw salad + bun",
                ""};
        ArrayList<String> keywords[] = new ArrayList[]{new ArrayList<String>(Arrays.asList("chicken","strips","fries",
                "coleslaw","salad","bun","garlic mayonnaise sauce","dynamite sauce","drink","bread", "KFC")),
                new ArrayList<String>(Arrays.asList("chicken","strips","popcorn","fries","coleslaw","bun","garlic mayonnaise sauce","dynamite sauce","drink","bread", "KFC")),
                new ArrayList<String>(Arrays.asList("farkha","one piece","fries","bun","bread", "KFC")),
                new ArrayList<String>(Arrays.asList("chicken","2 pieces","rice","large rice","fries","bun","bread", "KFC")),
                new ArrayList<String>(Arrays.asList("samira","2 pieces","fries","bun","bread", "KFC")),
                new ArrayList<String>(Arrays.asList("chicken","3 pieces","fries","small fries","coleslaw","salad","bun","bread", "KFC")),
                new ArrayList<String>(Arrays.asList("samira","4 pieces","fries","small fries","coleslaw","salad","bun","bread", "KFC")),
                new ArrayList<String>(Arrays.asList("chicken","3 pieces","fries","medium fries","coleslaw","salad","bun","bread", "KFC"))
        };
        ArrayList<Item> items = new ArrayList<Item>();
        for (int i=0;i<names.length;i++) {
            items.add(new Item(names[i],"KFC", "chicken", prices[i], descriptions[i], keywords[i], urls[i]));
        }
        Section section = new Section(items);
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
        Section section2 = new Section(items2);
        menu.addSection(section2);
        restaurant.addMenu(menu);

        Restaurant restaurant_mac= new Restaurant("MAC","Fast food","MAC Cairo",
                "https://marketing.otlob.com/images/nl/otlob/mcdlogoL.png",
                null,null);
        Menu menu_mac = new Menu();
        String names_mac[] = {"Big Mac","Big Tasty","Fillet-O-Fish Sandwich","Quarter Pounder with Cheese","Double Cheeseburger Sandwich",
                "Chicken MACDO","Cheeseburger Sandwich","McArabia Kofta Sandwich"};
        String urls_mac[]={"https://assets.otlob.com/dynamic/images/products/60/60758_1442409163_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/60/60759_1442409162_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/60/60718_1442408805_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/60/60641_1442408011_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/60/60655_1442408008_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/97/97923_1442410681_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/97/97924_1442410680_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/60/60642_1442408011_ma.jpg",
        };
        double prices_mac[] ={20.90,31.37,12.72,22.72,18.64,31.82,39.54,9.54};
        String descriptions_mac[]={"100% grilled beef 2 patties, special Big Mac™ sauce, cheese, crunchy lettuce, fresh onions, pickle slices, sesame seed buns",
                "Pure grilled beef patty wrapped in 3 layers of Emmental cheese covered with a special tasty sauce, crunchy lettuce, fresh tomatoes & onions all wrapped in a sesame bun",
                "Pure white fish breaded patty covered with tasty tartar sauce and a layer of cheese - all wrapped in a fresh bun",
                "Pure grilled beef patties between 2 layers of cheese covered with ketchup, mustard, fresh onions & pickles - all wrapped in a sesame seed bun",
                "Two slices of golden American cheese with two 100% all-beef patties, pickles, onions, ketchup and mustard on a toasted bun",
                "100% white meat grilled chicken breast filet covered with crunchy lettuce and tasty mayonnaise - all wrapped in a fresh bun",
                "It doesn`t get better than this.. a grilled beef patty with ketchup, mustard, pickles, onions and a cheese slice in a bun.",
                "Grilled kofta 2 patties covered with tehina sauce, fresh lettuce, tomatoes & onions all wrapped in savory Arabic bread",
        };
        ArrayList<String> keywords_mac[] = new ArrayList[]{new ArrayList<String>(Arrays.asList("Bread","Beef burger","beef","burger", "Cheese", "Lettuce", "Pickles", "Onions", "Our special sauce","sauce", "sesame", "MAC", "macdonalds")),
                new ArrayList<String>(Arrays.asList("Bread","Beef burger","beef","juicy","beef","emmental","cheese","Emmental cheese","Tomato","Lettuce","Onions","sauce", "mac", "macdonalds")),
                new ArrayList<String>(Arrays.asList("Bread","Fillet fish","fish","fillet","Tartar sauce","tartar","Cheese", "mac", "macdonalds")),
                new ArrayList<String>(Arrays.asList("Bread","Beef burger","beef","Cheese","Ketchup","Mustard","Pickles","Onions", "mac", "macdonalds")),
                new ArrayList<String>(Arrays.asList("Bread","Beef burger","beef","ketchup","mustard","onions","pickles","beef", "mac", "macdonalds")),
                new ArrayList<String>(Arrays.asList("Bread","Chicken","Lettuce","Mayo","fresh", "MAC", "macdonalds")),
                new ArrayList<String>(Arrays.asList("Bread","Beef burger","beef","Cheese","Ketchup","Onions","Pickles","beef", "mac", "macdonalds")),
                new ArrayList<String>(Arrays.asList("Arabic bread","Beef kofta","beef","Kofta","Lettuce","Tomatoes","Onions","Garlic","bun","bread", "mac", "macdonalds"))
        };
        ArrayList<Item> items_mac = new ArrayList<Item>();
        for (int i=0;i<names.length;i++) {
            items_mac.add(new Item(names_mac[i],"MAC", "Burger", prices_mac[i], descriptions_mac[i], keywords_mac[i], urls_mac[i]));
        }
        Section section_mac = new Section(items_mac);
        menu_mac.addSection(section_mac);
        restaurant_mac.addMenu(menu_mac);


        String response1 = restTemplate.postForObject(context.getString(R.string.url)+"restaurant/add",restaurant_mac,String.class);
        String response = restTemplate.postForObject(context.getString(R.string.url)+"restaurant/add",restaurant,String.class);

        return response;
    }
}
