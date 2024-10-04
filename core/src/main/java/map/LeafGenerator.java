package map;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeafGenerator {
    private static final int MAX_LEAF_WIDTH = 400;
    private static final int MAX_LEAF_HEIGHT = 600;
    private List<Leaf> leafs = new ArrayList<>(); // список Leaf
    private Random random = new Random();

     public List<Leaf> getLeafs() {
        return leafs;
    }

    public void generateLeaves(int width, int height,int maxRooms) {
        // сначала создаем Leaf, который будет "корнем" всех Leaf


        Leaf root = new Leaf(0, 0, width, height);
        leafs.add(root);
        boolean didSplit = true;

        // продолжаем цикл, пока можем делить Leaf
        while (didSplit) {
            didSplit = false;
            for (int i=0;i<leafs.size();i++)  {

                if (leafs.get(i).leftChild == null && leafs.get(i).rightChild == null) { // если этот Leaf еще не разделен...
                    // если Leaf слишком большой или 75% шанса...
                    if (leafs.get(i).width > MAX_LEAF_WIDTH || leafs.get(i).height > MAX_LEAF_HEIGHT || random.nextDouble()>0.25) {
                        if (leafs.get(i).split()) { // разделить Leaf!
                            // если мы разделили, добавим дочерние Leaf в список для следующей итерации
                            leafs.add(leafs.get(i).leftChild);
                            leafs.add(leafs.get(i).rightChild);
                            didSplit=true;
                        }
                    }
                }
            }
        }
        root.createRooms();
    }

}

