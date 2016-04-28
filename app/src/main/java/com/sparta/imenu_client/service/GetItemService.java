package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.ItemActivity;
import com.sparta.imenu_client.model.Item;
import com.squareup.picasso.Picasso;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 4/27/2016.
 */

public class GetItemService extends AsyncTask<Void, Void, Item>{
    ItemActivity callingActivity;
    Exception error;
    ProgressBar progressBar;
    long itemId;
    public GetItemService() {
    }

    public GetItemService(ItemActivity callingActivity,ProgressBar progressBar,long itemId) {
        this.callingActivity = callingActivity;
        this.progressBar = progressBar;
        Log.i("fel constr.", String.valueOf(itemId));
        this.itemId= itemId;
    }

    @Override
    protected void onPreExecute() {

        callingActivity.setProgressBarIndeterminateVisibility(true);
    }
    @Override
    protected Item doInBackground(Void... params) {

        Log.i("getItem service", "service started");
        final String url = callingActivity.getString(R.string.url)+"item/getById?id="+itemId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            Item item = restTemplate.getForObject(url, Item.class);
            return item;
        }
        catch (Exception e){
            error=e;
            Log.i("getItem service","error caught");
            return null;
        }
    }
    @Override
    protected void onPostExecute(Item item) {

        if(item!=null) {
            progressBar.setVisibility(View.GONE);
            Log.i("getItem service", "binding layout");
            Log.i("getItem service", "id=" + itemId);
            ImageView itemImage = (ImageView) callingActivity.findViewById(R.id.item_image);
            TextView itemName = (TextView) callingActivity.findViewById(R.id.item_name);
            TextView itemRestName = (TextView) callingActivity.findViewById(R.id.item_restaurant_name);
            TextView itemDescription = (TextView) callingActivity.findViewById(R.id.item_description);
            TextView itemPrice = (TextView) callingActivity.findViewById(R.id.item_price);
            TextView itemRating = (TextView) callingActivity.findViewById(R.id.item_rating);

            Picasso.with(callingActivity).load(item.getPicture())
                    .error(R.drawable.no_image)
                    .placeholder(R.drawable.loading_image)
                    .into(itemImage);
            itemName.setText(item.getName());
            itemRestName.setText(item.getRestaurantName());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(Double.toString(item.getPrice()));
            itemRating.setText(Double.toString(item.getRating()));
        }
        else
            Toast.makeText(callingActivity,"There is a problem in the server\nPlease try again later",Toast.LENGTH_SHORT).show();
    }
}
