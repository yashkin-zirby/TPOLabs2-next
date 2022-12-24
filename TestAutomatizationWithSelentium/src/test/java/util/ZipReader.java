package util;

import org.apache.commons.lang3.arch.Processor;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipReader {
    public static List<String> getFileNamesFromZipArchive(String ArchiveName){
        List<String> names = new ArrayList<String>();
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(ArchiveName)))
        {
            ZipEntry entry;
            while((entry=zin.getNextEntry())!=null){
                names.add(entry.getName());
                zin.closeEntry();
            }
        }
        catch(Exception ex){

            LogManager.getRootLogger().error("Cannot open archive");
            return null;
        }
        return names;
    }
    public static boolean archiveContainFileWithNameLike(String archiveName, String fileName){
        List<String> names = getFileNamesFromZipArchive(archiveName);
        if(names == null) return false;
        for (String name:names) {
            if(name.toLowerCase().contains(fileName.toLowerCase()))return true;
        }
        return false;
    }
    public static File getLastModified(File[] files)
    {
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }
}
