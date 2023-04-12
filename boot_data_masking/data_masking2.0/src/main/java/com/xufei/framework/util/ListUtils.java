package com.xufei.framework.util;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class ListUtils {
    public static<E> List<E> deepCopy(List<E> sourceList) {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        List<E> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            out = new ObjectOutputStream(byteOut);
            out.writeObject(sourceList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            in = new ObjectInputStream(byteIn);

            dest = (List<E>) in.readObject();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(out)){
                    out.close();
                }
                if (Objects.nonNull(in)){
                    in.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dest;
    }
}
