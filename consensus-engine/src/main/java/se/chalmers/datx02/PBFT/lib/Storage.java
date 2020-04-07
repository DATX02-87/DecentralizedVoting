package se.chalmers.datx02.PBFT.lib;

import se.chalmers.datx02.PBFT.State;

import java.io.*;

public class Storage {
    /**
     * Used to write to storage
     * @param storage_location
     */
    public static void save_storage(String storage_location, State obj) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(storage_location + "/savedConfig.bin");
            // todo: Create new savedConfig.bin if not already exists
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(obj);
        objectOut.close();
        fileOut.close();
    }

    /**
     * Used to read from storage
     * @param storage_location
     */
    public static Object get_storage(String storage_location) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(storage_location + "/savedConfig.bin");
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Object obj = objectIn.readObject();
        objectIn.close();
        fileIn.close();

        return obj;
    }
}
