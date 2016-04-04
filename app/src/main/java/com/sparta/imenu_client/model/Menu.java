package com.sparta.imenu_client.model;

import java.util.ArrayList;

/**
 * Created by hamed on 3/19/16.
 */
public class Menu {
    private int id;
    private ArrayList<Section> sections;
    public Menu (){
        sections = new ArrayList<Section>();
    }
//    public Menu(ArrayList<Section> sections) {
//        this.sections = sections;
//    }
    public ArrayList<Section> getSections() {
        return sections;
    }

    public void addSection (Section section) {
        sections.add(section);
        // call the service
    }
    public void removeSection (int sectionId) {
        ArrayList<Section> temp = new ArrayList<Section>();
        for (int i=0;i<sections.size();i++){
            if (sections.get(i).getId()!=sectionId){
                temp.add(sections.get(i));
            }
        }
        sections = temp;
        // call the service
    }
}
