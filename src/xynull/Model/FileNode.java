package xynull.Model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class for a file-node.
 * Created by XyNull on 2016/10/10.
 */
public class FileNode extends Directory{
    public FileNode(String p){
        if(new File(p).isDirectory())
            path = p;
        parent = new File(p).getParent();
        generateBytes();
        generateLines();
    }

    float generateBytes(){
        float s = 0;
        if(pathIsEmpty(path) || new File(path).isDirectory())
            return -1;
        try {
            Path p = Paths.get(path);
            s = Files.size(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    void generateLines(){
        Path p = Paths.get(path);
        try {
            lines = Files.readAllLines(p, Charset.defaultCharset()).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param p a path for a file.
     * @param ignore a boolean for ignoring blank lines or not.
     * @return sum lines in this file.
     */
    public int generateLines(String p, boolean ignore){
        if(pathIsEmpty(p))
            return 0;
        Path path = Paths.get(p);

        List<String> lines = null;
        try {
            lines = Files.readAllLines(path, Charset.defaultCharset());
            if(ignore)
                while(lines.remove(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return lines != null? lines.size(): 0;
        }
    }
}
