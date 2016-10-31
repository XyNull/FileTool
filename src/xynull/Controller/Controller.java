package xynull.Controller;

import xynull.Model.FileNode;
import xynull.Model.FolderNode;
import xynull.View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Controller {
    public void preProcess(){//String rootFolder,String tar, List<Boolean> add
        JButton buttonRun = new View().getButtonRun();
        buttonRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });
        List<String> targets = new ArrayList<>();
        List<Boolean> addons = new ArrayList<>();

        //FolderNode root = new FolderNode(rootFolder);
        //List<FileNode> files = root.getSonFile();
        //List<FolderNode> folders = root.getSonFolder();
    }

    void init(){
        JCheckBox checkBox = new View().getCheckBoxIgnore();
        checkBox.setVisible(false);
    }
}
