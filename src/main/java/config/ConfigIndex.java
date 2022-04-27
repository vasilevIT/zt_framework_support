package config;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.externalizer.StringCollectionExternalizer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigIndex extends FileBasedIndexExtension<String, List<String>> {
    public static final ID<String, List<String>> identity = ID.create("ZtFrameworkTools.ConfigIndex");

    @Override
    public @NotNull ID<String, List<String>> getName() {
        return identity;
    }

    @Override
    public @NotNull DataIndexer<String, List<String>, FileContent> getIndexer() {
//        TODO remove
//        System.out.println("DataIndexer");
        return file -> {
//            TODO remove
//            System.out.println("DataIndexer, file:" + file);
            ConfigParser parser = new ConfigParser();

            PsiFile psiFile = file.getPsiFile();

            PsiElement openTag = psiFile.getFirstChild();
//            TODO remove
//            System.out.println("openTag " + openTag);
            String configContent = openTag.getText();

            final Map<String, List<String>> result =  parser.parse(configContent);
//            TODO remove
//            System.out.println("DataIndexer, result:" + result);
            return  result;
        };
    }

    @Override
    public @NotNull KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    @Override
    public @NotNull DataExternalizer<List<String>> getValueExternalizer() {
        return StringCollectionExternalizer.STRING_LIST_EXTERNALIZER;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public FileBasedIndex.@NotNull InputFilter getInputFilter() {

        return file -> {
            if (file == null) {
                return false;
            }
            if (file.getUrl() == null) {
                return false;
            }

//            TODO remove
//            System.out.println("file");
//            System.out.println(file);
//            System.out.println(file.getExtension());
//            System.out.println(file.getUrl());
//            System.out.println("res = " + (file.getExtension().equals("dat") && file.getUrl().contains("/_settings/")));
            return (file.getExtension().equals("dat") && file.getUrl().contains("/_settings/"));
        };
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }
}