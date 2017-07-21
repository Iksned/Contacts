package ConModel;

import java.io.*;


public class Utilits { //to DAO interface

    public static void SaveCat(Catalog cat)
    {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File("temp.catal")));
            outputStream.writeObject(cat);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Save Error");
        }
    }

    public static Catalog LoadCat()
    {
        Catalog catalog = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("temp.catal"));
            catalog = (Catalog)inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load Error");
        }
        if (catalog != null)
            return catalog;
        else
            return null;
    }
}
