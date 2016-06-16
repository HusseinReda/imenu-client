package com.sparta.imenu_client.service;

import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Order;
import com.sparta.imenu_client.model.OrderCard;
import com.sparta.imenu_client.model.User;
import com.sparta.imenu_client.model.UserSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sabaa on 5/2/16.
 */
public class Auxiliary {

    private static User currentUser;
    public static String connectedToRest=null;
    public static Order order;
    public static int serviceTableSecretNumber;

    public static void initOrder(){
        order=new Order();
        order.setState(1);
        order.setUser(getCurrentUser());
    }

    public static void addToOrder(OrderCard orderCard){
        order.addOrderCard(orderCard);
    }

    public static void removeFromOrder(OrderCard orderCard){
        order.removeOrderCard(orderCard);
    }

    public static List<Item> sortByRelevanceModified(List<Item> items, String[] queries) {

        Map<Integer, List<Item>> hashMap = new HashMap<Integer, List<Item>>();
        for(int i=0;i<items.size();i++){
            int rank = 0;
            for(int j=0;j<queries.length;j++){
                List<String> keywords=items.get(i).getKeywords();
                for(int k=0;k<keywords.size();k++) {
                    if (keywords.get(k).equals(queries[j]))
                        rank+=1000000;
                }
            }
            rank+=items.get(i).getName().charAt(0)*100+items.get(i).getName().charAt(1)*10+items.get(i).getName().charAt(2);
            if(hashMap.containsKey(rank)) {
                hashMap.get(rank).add(items.get(i));
            }
            else{
                List<Item> newList = new ArrayList<Item>();
                newList.add (items.get(i));
                hashMap.put(rank, newList);
            }
        }
        List<Item> result = new ArrayList<Item>();
        Iterator it = hashMap.entrySet().iterator();
        for (Map.Entry<Integer, List<Item>> entry : hashMap.entrySet()) {
            List<Item> vec = entry.getValue();
            for(int j=0;j<vec.size();j++){
                result.add(vec.get(j));
            }
        }
        Collections.reverse(result);
        return result;
    }

    public static ArrayList<UserSpec> restrictions;
    public static ArrayList<UserSpec> healthIssues;

    public static void addRestriction(UserSpec restriction){
        if(restrictions==null)
            restrictions = new ArrayList<UserSpec>();
        for(int i=0;i<restrictions.size();i++)
            if(restrictions.get(i).getId()==restriction.getId())
                return;

        restrictions.add(restriction);
    }

    public static void addHealthIssue(UserSpec healthIssue){
        if(healthIssues==null)
            healthIssues = new ArrayList<UserSpec>();
        for(int i=0;i<healthIssues.size();i++)
            if(healthIssues.get(i).getId()==healthIssue.getId())
                return;
        healthIssues.add(healthIssue);
    }

    public static UserSpec getRestrictionByName(String name){
        for(int i=0;i<restrictions.size();i++){
            if(restrictions.get(i).getName().equals(name))
                return restrictions.get(i);
        }
        return null;
    }

    public static UserSpec getHealthIssueByName(String name){
        for(int i=0;i<healthIssues.size();i++){
            if(healthIssues.get(i).getName().equals(name))
                return healthIssues.get(i);
        }
        return null;
    }

    public static ArrayList<String> searchInAllHealthIssues(User user, String itemKeyword){
        ArrayList<UserSpec> healthIssuesToSearchIn = new ArrayList<UserSpec>();
        if(user==null)
            healthIssuesToSearchIn=healthIssues;
        else
            healthIssuesToSearchIn= (ArrayList<UserSpec>) user.getHealthIssues();

        ArrayList<String> returnedNotes= new ArrayList<String>();
        for(int i=0;i<healthIssuesToSearchIn.size();i++){
            List<String> healthIssueKeywords=healthIssuesToSearchIn.get(i).getKeywords();
            for(int c=0;c<healthIssueKeywords.size();c++){
                if(itemKeyword.equals(healthIssueKeywords.get(c))){
                    returnedNotes.add(healthIssuesToSearchIn.get(i).getNote());
                    break;
                }
            }
        }
        return returnedNotes;
    }
    public static ArrayList<String> searchInAllRestrictions(User user, String itemKeyword){
        ArrayList<UserSpec> restrictionsToSearchIn = new ArrayList<UserSpec>();
        if(user==null)
            restrictionsToSearchIn=restrictions;
        else
            restrictionsToSearchIn= (ArrayList<UserSpec>) user.getRestrictions();
        ArrayList<String> returnedNotes= new ArrayList<String>();
        for(int i=0;i<restrictionsToSearchIn.size();i++){
            List<String> restrictionKeywords=restrictionsToSearchIn.get(i).getKeywords();
            for(int c=0;c<restrictionKeywords.size();c++){
                if(itemKeyword.equals(restrictionKeywords.get(c))){
                    returnedNotes.add(restrictionsToSearchIn.get(i).getNote());
                    break;
                }
            }
        }
        return returnedNotes;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Auxiliary.currentUser = currentUser;
    }
}
