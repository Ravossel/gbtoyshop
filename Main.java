import java.io.*;
import java.util.*;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight; // Вес в % от 100

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }
}

class ToyStore {
    private List<Toy> toys;

    public ToyStore() {
        toys = new ArrayList<>();
    }

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateWeight(int toyId, double weight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(weight);
                return;
            }
        }
        System.out.println("Такой игрушки не найдено.");
    }

    public Toy drawToy() {
        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }
        double random = Math.random() * totalWeight;
        double cumulativeWeight = 0;
        for (Toy toy : toys) {
            cumulativeWeight += toy.getWeight();
            if (random <= cumulativeWeight) {
                if (toy.getQuantity() > 0) {
                    toy.decreaseQuantity(1);
                    return toy;
                } else {
                    System.out.println("Извините, данная игрушка закончилась.");
                    return null;
                }
            }
        }
        return null;
    }

    public void saveToyToFile(Toy toy, String filename) {
        try (FileWriter writer = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(toy.getId() + "," + toy.getName());
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ToyStore store = new ToyStore();
        store.addToy(new Toy(1, "Мяч", 10, 25));
        store.addToy(new Toy(2, "Кукла", 15, 35));
        store.addToy(new Toy(3, "Машинка", 20, 40));

        Toy drawnToy = store.drawToy();
        if (drawnToy != null) {
            System.out.println("Выбрана игрушка: " + drawnToy.getName());
            store.saveToyToFile(drawnToy, "prize.txt");
        }

        // Пример изменения веса игрушки с id=2
        store.updateWeight(2, 50);
    }
}
