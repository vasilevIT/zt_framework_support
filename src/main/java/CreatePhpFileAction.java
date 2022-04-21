import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
//import com.intellij.openapi.util.NlsActions;
//import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
//import org.jetbrains.annotations.NonNls;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CreatePhpFileAction extends CreateFileFromTemplateAction {

    public static final String LIBRARY_PHP_CLASS = "Library php class";
    public static final String MODULE_PHP_CLASS = "Module php class";

    public CreatePhpFileAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.@NotNull Builder builder) {
        builder.setTitle("Create a new inc.php file. Enter name").addKind("Library class", null, LIBRARY_PHP_CLASS).addKind("Module class", null, MODULE_PHP_CLASS);
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return "Create A New php class File";
    }


    @Override
    protected boolean isAvailable(DataContext dataContext) {
        return true;
    }

    @Override
    protected PsiFile createFile(String name, String templateName, PsiDirectory dir) {
        PsiFile file = null;
        if (templateName.equals(LIBRARY_PHP_CLASS)) {
            file =  dir.createFile(name + ".inc.php");
            VirtualFile virtualFile =  file.getVirtualFile();
            String path = virtualFile.getPath();
            String[] paths = path.split("\\/");
            if (Arrays.stream(paths).anyMatch(x-> x.equals("_lib"))) {
                System.err.println("Library in _lib");
            }
        } else if (templateName.equals(MODULE_PHP_CLASS)) {
            file = dir.createFile(name + ".inc.php");
            VirtualFile virtualFile =  file.getVirtualFile();
            String path = virtualFile.getPath();
            String[] paths = path.split("\\/");
            if (Arrays.stream(paths).anyMatch(x-> x.equals("_lib"))) {
                System.err.println("Module in _lib");
            } else {
//                File plainFile = new File(path);
                try {
                    FileWriter myWriter = new FileWriter(path);
                    myWriter.write("<?php" +
                            "class U__" + name + "{" +
                            "" +
                            "}" +
                            "?>");

                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return file;
    }

}
