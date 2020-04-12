package se.chalmers.datx02.FBA.lib;

import se.chalmers.datx02.FBA.lib.exceptions.StoredInMemory;

import java.io.*;

public class Storage {

    private static final String stored_fileName = "configStorage";
    private static final String stored_fileSuffix = ".bin";
    /**
     * Used to write to storage
     * @param storage_location specifies the location of storage
     */
    public static void save_storage(String storage_location, Object obj) throws IOException, StoredInMemory {
        if(storage_location == "memory")
            throw new StoredInMemory("Object is stored in memory, skipping save_storage");

        File file = new File(storage_location + "/" + stored_fileName + stored_fileSuffix);

        file.createNewFile();

        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(obj);
        objectOut.close();
        fileOut.close();
    }

    /**
     * Used to read from storage
     * @param storage_location specifies the location of storage
     */
    public static Object get_storage(String storage_location) throws IOException, ClassNotFoundException, StoredInMemory {
        if(storage_location == "memory")
            throw new StoredInMemory("Object is stored in memory, skipping get_storage");

        File file = new File(storage_location + "/" + stored_fileName + stored_fileSuffix);
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Object obj = objectIn.readObject();
        objectIn.close();
        fileIn.close();

        return obj;
    }
}
