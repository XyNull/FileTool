package xynull.Model;

import java.io.File;
import java.math.BigDecimal;

/**
 * An abstract class for a file-node or a folder-node.
 * Created by XyNull on 2016/10/29.
 */
public abstract class Directory {
    String path;
    float size;
    String parent;
    int lines;

    /**
     * get file or folder's path as a String.
     * @return path.
     */
    public String getPath() {
        return path;
    }

    float getSize(){
        return size;
    }

    /**
     * get file or folder's size as a String.
     * @return size，suffer with B or KB or MB or GB.
     */
    public String getSizeFormat() {
        if(size < 1<<10)
            return size + "B";
        if (size < 1<<20)
            return divide(size,1<<10) + "KB";
        if (size < 1<<30)
            return divide(size,1<<20) + "MB";
        else return divide(size,1<<30) + "GB";
    }

    /**
     * get file or folder's parent as a String.
     * @return parent.
     */
    public String getParent() {
        return parent;
    }

    /**
     * sum lines in a file or a folder as a int.
     * @return size of lines.
     */
    public int getLines() {
        return lines;
    }

    abstract float generateBytes();

    abstract void generateLines();

    /**
     *
     * @param path which path you need to judge.
     * @return is legal or not.
     */
    public boolean pathIsEmpty(String path) {
        return path == null || path.length() < 1 || !new File(path).exists();
    }

    static double divide(float d1,float d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.divide(bd2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
