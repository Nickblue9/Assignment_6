import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Dictionary {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        String word, define;
        try {
            Scanner scanner = new Scanner(new File(args[0]));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                for (int j = 0; j < line.length(); ++j) {
                    if (line.charAt(j) >= 97 && line.charAt(j) <= 122 || (line.contains(" ")))
                        break;
                    if (++j == line.length()) {
                        if(treeHas(tree.root,line))
                            break;
                        word = line;
                        tree.root = tree.insert(tree.root, word);
                    }
                }
            }
            System.out.println("Current tree: ");
            AVLTree.print2DUtil(tree.root, 0, 5);
        }catch(IOException e){e.printStackTrace();}

    }

    private static boolean treeHas(Node root, String line) {
        Node current = root;
        if (current == null)
            return false;
        if(current.key.equals(line))
            return true;
        else{
            if(alphabetical(current.key, line)){
                if(current.right!=null)
                    treeHas(current.right,line);
                else
                    return false;
            }
            else{
                if(current.left!=null)
                    treeHas(current.right,line);
                else
                    return false;
            }
        }
        return false;
    }

    private static boolean alphabetical(String i, String j){
        return i.compareTo(j) < 0;
    }
}

class AVLTree {
    Node root;

    int height(Node N) {
        if (N == null)
            return 0;
        return N.height;
    }

    int max(int a, int b) {
        return Math.max(a, b);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        // Perform rotation
        x.right = y;
        y.left = T2;
        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
        // Return new root
        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        // Perform rotation
        y.left = x;
        x.right = T2;
        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        // Return new root
        return y;
    }

    // Get Balance factor of node N
    int getBalance(Node N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    Node insert(Node node, String key) {
        /* 1. Perform the normal BST insertion */
        if (node == null)
            return (new Node(key));

        if (!Alphabetical(key,node.key))//(key < node.key)
            node.left = insert(node.left, key);
        else if (Alphabetical(key,node.key))
            node.right = insert(node.right, key);
        else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left), height(node.right));

		/* 3. Get the balance factor of this ancestor
			node to check whether this node became
			unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && !(Alphabetical(key,node.left.key)))//key < node.left.key)
            return rightRotate(node);

        // Left Right Case
        if (balance > 1 && Alphabetical(key,node.left.key)){//key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && Alphabetical(key,node.right.key))//key > node.right.key)
            return leftRotate(node);

        // Right Left Case
        if (balance < -1 && !(Alphabetical(key,node.right.key))){//key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }
    static Boolean Alphabetical(String a, String b){
        int compare = a.compareToIgnoreCase(b);
        //a comes first
        if(compare<0)
            return false;
            //b comes first
        else if(compare>0)
            return true;
        //a and b are the same
        return false;

    }

    static void print2DUtil(Node root, int space, int COUNT) {
        // Base case
        if (root == null)
            return;
        // Increase distance between levels
        space += COUNT;
        // Process right child first
        print2DUtil(root.right, space, COUNT);
        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.key + "\n");
        // Process left child
        print2DUtil(root.left, space, COUNT);
    }

}

class Node {
    int height;
    String key;
    ArrayList<String> definition;
    Node left;
    Node right;
    Node(String x) {
        key = x;
        height = 1;
    }
}





