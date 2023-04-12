package com.xufei.framework.util;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ik分词器的工具类
 */
public class MyIKAnalyzer {
    /**
     *
     * @param str 作为一个词语传入 length>1
     * @return
     * @throws IOException
     */
    public static List<String> participle(String str) throws IOException {

        StringReader sr = new StringReader(str);
        IKSegmenter ik = new IKSegmenter(sr,true);
        Lexeme lex = null;
        List<String> list=new ArrayList<>();
        while ((lex=ik.next())!=null){
            if(lex.getLexemeText().length()>1){
                list.add(lex.getLexemeText());
            }
        }
        return list;
    }
}
