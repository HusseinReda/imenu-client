package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Pair;

import com.sparta.imenu_client.model.Section;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hamed on 3/19/16.
 */
public class AddSectionToMenuService extends AsyncTask {

    private int menuId;
    private Section section;
    private String url;

    public AddSectionToMenuService(int menuId, Section section, String url) {
        this.menuId = menuId;
        this.section = section;
        this.url = url;
    }

    @Override
    protected String doInBackground(Object... params) {
        Pair <Integer, Section> sectionData = new Pair<>(menuId, section);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate. getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate .postForObject(url, sectionData, String.class);
        return result;
    }
}
