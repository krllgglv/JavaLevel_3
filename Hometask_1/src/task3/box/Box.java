package task3.box;

import task3.fruit.Fruit;

import java.util.ArrayList;

public class Box <T extends Fruit> {
    private ArrayList<T> container;

    public Box() {
        this.container = new ArrayList<>();
    }

    public ArrayList<T> getContainer() {
        return container;
    }

    public boolean compare (Box <?> box){
        return this.getWeight() == box.getWeight();
    }

    public void addFruit(T fruit){
        container.add(fruit);
    }

    public float getWeight(){
        if (container.size()==0){
            return 0.0f;
        }
        return container.size()*container.get(0).getWeight();
    }
//    решил  добавлять метод, возвращающий container целиком (чотбы использовать addAll)
//    хотя можно было имеющимся по условию задачи методом add через цикл
    public void pourEverythingToAnotherBox(Box<T> destBox){
        destBox.getContainer().addAll(this.container);
        this.container.clear();
    }


}
