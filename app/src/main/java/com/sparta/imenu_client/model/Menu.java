package com.sparta.imenu_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamed on 3/19/16.
 */
public class Menu {
    private int id;
    private List<Section> sections;
    private int restaurantId;

    public Menu(int restaurantId, List<Section> sections) {
        this.restaurantId = restaurantId;
        this.sections = sections;
    }

    public List<Section> getSections() {
        return sections;
    }

    public int getRestaurantId() {
        return restaurantId;
    }
    public void addSection (Section section) {
        sections.add(section);
        // call the service
    }
    public void removeSection (int sectionId) {
        List<Section> temp = new ArrayList<Section>();
        for (int i=0;i<sections.size();i++){
            if (sections.get(i).getId()!=sectionId){
                temp.add(sections.get(i));
            }
        }
        sections = temp;
        // call the service
    }
}
