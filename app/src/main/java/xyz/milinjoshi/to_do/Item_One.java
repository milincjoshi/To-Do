package xyz.milinjoshi.to_do;

/**
 * Created by milinjoshi on 3/27/16.
 */
public class Item_One {
    String name;
    int value;

    Item_One(String name, int value){
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
