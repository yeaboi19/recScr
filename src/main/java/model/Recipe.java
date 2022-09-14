package model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<String> ingredients;
    private String instruction;

    public Recipe() {
    }

    public Recipe(String name, ArrayList<String> ingredients, String instruction) {
        this.name = name;
        this.ingredients = ingredients;
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
