package com.yangzhichao.yzclib.file;

import java.io.File;

public class RenameFile {

    public void rename(String oldName, String newName) {
        boolean file = new File(oldName).renameTo(new File(newName));


    }

    public static void main(String[] args) {
        String name = "/Users/yangzhichao/Downloads/112的副本.jpg";
        String newName="/Users/yangzhichao/Downloads/112.jpg";
        new RenameFile().rename(name,newName);


    }


}
