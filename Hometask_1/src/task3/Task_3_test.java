package task3;

import task3.box.Box;
import task3.fruit.Apple;
import task3.fruit.Orange;

public class Task_3_test {
    public static void main(String[] args) {
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox1 = new Box<>();
//        добавляем фрукты
        appleBox1.addFruit(new Apple());
        appleBox1.addFruit(new Apple());
        appleBox1.addFruit(new Apple());

        orangeBox1.addFruit(new Orange());
        orangeBox1.addFruit(new Orange());
    // сравниваем вес
        System.out.println(appleBox1.compare(orangeBox1));
// пересыпаем из 1 коробки в другую
        appleBox1.pourEverythingToAnotherBox(appleBox2);
        System.out.println(appleBox1.getContainer().size());
        System.out.println(appleBox2.getContainer().size());
    }


}


