package xynull.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for a folder-node.
 * Created by XyNull on 2016/10/29.
 */
public class FolderNode extends Directory {
    List<FileNode> sonFile = new ArrayList<>();
    List<FolderNode> sonFolder = new ArrayList<>();

    public List<FileNode> getSonFile() {
        return sonFile;
    }
    public List<FolderNode> getSonFolder() {
        return sonFolder;
    }
    public String getParent() {
        return parent;
    }

    public FolderNode(String p){
        if(new File(p).isDirectory())
            path = p;
        parent = new File(p).getParent();
        getSonFile(p);
        generateBytes();
        generateLines();
    }

    void getSonFile(String path) {
        if(pathIsEmpty(path))
            return;

        try {
            File[] files = new File(path).listFiles();
            if (files == null)
                return;

            for (File f :
                    files) {
                if (f.isDirectory())
                    sonFolder.add(new FolderNode(f.toString()));
                if (f.isFile())
                    sonFile.add(new FileNode(f.toString()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    float generateBytes() {
        float res = 0;
        for (FileNode file :
                sonFile) {
            res += file.size;
        }
        for (FolderNode folder :
                sonFolder) {
            res += folder.getSize();
        }
        return res;
    }

    void generateLines(){
        for (FileNode file :
                sonFile) {
            lines += file.getLines();
        }
    }

    List<String> sumFlie(String target){
        List<String> res = new ArrayList<>();

        sonFile.stream()
                .filter(p -> new File(p.getPath()).getName().contains(target))
                .forEach(p -> res.add(p.getPath()));
        return res;
    }

    public boolean pathIsEmpty(String path) {
        return path == null || path.length() < 1 || !new File(path).exists();
    }
}
