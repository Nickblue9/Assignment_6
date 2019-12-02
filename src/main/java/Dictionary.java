import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Dictionary {
    public static void main(String [] args){
        Dictionary dictionary = new Dictionary();
        dictionary.createAVLTree(new File(args[0]));
    }

    private void createAVLTree(File f){

        try {
            Scanner scanner = new Scanner(f);
            ArrayList<String> words = new ArrayList<>();
            
        }catch(IOException e){e.printStackTrace();}
    }
}





