import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
//import com.intellij.openapi.util.NlsActions;
//import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
//import org.jetbrains.annotations.NonNls;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.PhpIcons;
import org.jetbrains.annotations.NotNull;
import util.FileUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CreatePhpFileAction extends CreateFileFromTemplateAction {

    public static final String LIBRARY_PHP_CLASS = "Library php class";
    public static final String MODULE_PHP_CLASS = "Module php class";
    public static final String LIBRARY_CLASS = "Library class";
    public static final String MODULE_CLASS = "Module class";

    private Project project;

    public CreatePhpFileAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.@NotNull Builder builder) {
        this.project = project;
        builder.setTitle("Create a new inc.php file. Enter name").addKind(LIBRARY_CLASS, PhpIcons.CLASS, LIBRARY_PHP_CLASS).addKind(MODULE_CLASS, PhpIcons.CLASS, MODULE_PHP_CLASS);
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
        System.out.println("project.getBasePath()");
        System.out.println(project.getBasePath());
        PsiFile file = null;
        if (templateName.equals(LIBRARY_PHP_CLASS)) {
            if (!FileUtil.isInLibDirectory(dir.getVirtualFile().getPath())) {
                // Create library  directory, if current path hasn't the _lib directory
                PsiDirectory libraryDirecotry = dir.findSubdirectory("_lib");
                if (libraryDirecotry == null) {
                    dir = dir.createSubdirectory("_lib");
                } else {
                    dir = libraryDirecotry;
                }
            }

            file = dir.createFile(FileUtil.getFileNameByClassName(name));
            VirtualFile virtualFile = file.getVirtualFile();
            String absolutePath = virtualFile.getPath();
            String path = virtualFile.getPath().replaceFirst(project.getBasePath(), "");
            try {
                FileWriter myWriter = new FileWriter(absolutePath);
                String fullClassName = FileUtil.getLibraryNameByPath(path);
                myWriter.write("<?php" + System.lineSeparator().repeat(3) + "class " + fullClassName + " {" + System.lineSeparator().repeat(3) + "}" + System.lineSeparator().repeat(2) + "?>");

                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (templateName.equals(MODULE_PHP_CLASS)) {
            file = dir.createFile(FileUtil.getFileNameByClassName(name));
            VirtualFile virtualFile = file.getVirtualFile();
            String absolutePath = virtualFile.getPath();
            String path = virtualFile.getPath().replaceFirst(project.getBasePath(), "");
            if (FileUtil.isInLibDirectory(path)) {
                System.err.println("Module in _lib");
            } else {
                try {
                    FileWriter myWriter = new FileWriter(absolutePath);
                    String fullClassName = FileUtil.getModuleNameByPath(path);
                    myWriter.write("<?php" + System.lineSeparator().repeat(3) + "class " + fullClassName + " {" + System.lineSeparator().repeat(3) + '\t' + "public static function main(array $args, array $messages): int {" + System.lineSeparator().repeat(3) + '\t' + '}' + System.lineSeparator() + "}" + System.lineSeparator().repeat(2) + "?>");

                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return file;
    }

}
