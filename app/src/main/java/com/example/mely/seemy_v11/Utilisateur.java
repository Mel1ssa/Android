package com.example.mely.seemy_v11;
import android.nfc.Tag;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MELY on 5/5/2017.
 */

public class Utilisateur implements Serializable {
    private String Id;
    private String Pseudo;
    private String Sexe;
    private String Age;
    private String Dist;
    private ArrayList<String> Tags;

    public  Utilisateur(String Id, String Pseudo, String Sexe, String Age, String Dist){
        this.Id=Id;
        this.Pseudo=Pseudo;
        this.Sexe=Sexe;
        this.Age=Age;
        this.Dist=Dist;
    }

    public String getPseudo() {
        return Pseudo;
    }

    public String getId() {
        return Id;
    }

    public String getSexe() {
        return Sexe;
    }

    public String getAge() {
        return Age;
    }

    public String getDist() {
        return Dist;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void add_Tag(String tag){
        this.Tags.add(tag);

    }
    public void remove_Tag(String tag){
        for (int i = 0; i <this.Tags.size(); i++)
        {
            if (Tags.get(i).equals(tag))
            {
                Tags.remove(Tags.get(i));
                break;
            }
        }

    }
    public void setId(String id) {
        Id = id;
    }

    public void setPseudo(String pseudo) {
        Pseudo = pseudo;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public void setAge(String age) {
        Age = age;
    }

    public void setDist(String dist) {
        Dist = dist;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "Id='" + Id + '\'' +
                ", Pseudo='" + Pseudo + '\'' +
                ", Sexe='" + Sexe + '\'' +
                ", Age='" + Age + '\'' +
                ", Dist='" + Dist + '\'' +
                '}';
    }
}
