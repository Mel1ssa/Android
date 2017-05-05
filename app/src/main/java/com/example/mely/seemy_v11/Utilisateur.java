package com.example.mely.seemy_v11;
import java.io.Serializable;

/**
 * Created by MELY on 5/5/2017.
 */

public class Utilisateur implements Serializable {
    private String Id;
    private String Pseudo;
    private String Sexe;
    private String Age;
    private String Dist;
    private String[] Tags;

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

    public String[] getTags() {
        return Tags;
    }

    public void add_Tag(String tag){
        this.Tags[this.Tags.length-1]=tag;

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

    public void setTags(String[] tags) {
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
