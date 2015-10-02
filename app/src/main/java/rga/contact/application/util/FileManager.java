package rga.contact.application.util;

import android.content.Context;
import android.os.Environment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private static String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    private static String IMAGES_PATH = ROOT_DIR + "/images/";

    private static FileManager instance;
    private File internalStorage;
    private Context context;

    public FileManager(Context context) {
        this.context = context;
        this.internalStorage = getInternalStorage();
    }

    public File openDirectory(String dir) throws Exception {
        try {
            if (isExternalStorageReadable()) {
                File directory = new File(dir);
                if (directory != null && directory.isDirectory())
                    return directory;
                return null;
            } else {
                throw new Exception("Not possible to open file "+ dir + " for reading");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public File openFileForReading(String path){
        try {
            if (isExternalStorageReadable()) {
                File file = new File(path);
                return file;
            } else {
                throw new Exception("Not possible to open file" + path + " for reading");
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }


    public synchronized static FileManager init(Context context){
        if ( instance == null )
            instance = new FileManager(context);
        return instance;
    }

    public synchronized static FileManager get(){
        return instance;
    }

    public File getFile(String absolutePath) {
        return new File(absolutePath);
    }


    public void deleteFile(String absolutePath){
        FileUtils.deleteQuietly(new File(absolutePath));
    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getInternalStorage(){
        File directory = null;
        if ( isExternalStorageReadable() )
            directory = this.context.getExternalFilesDir(IMAGES_PATH);
        return directory;
    }

    public String getInsternalStorage(){
        return internalStorage.getAbsolutePath();
    }

    public String copyFile(File source){
        try {
            FileUtils.copyFileToDirectory(source, internalStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IMAGES_PATH + source.getName();
    }
}
