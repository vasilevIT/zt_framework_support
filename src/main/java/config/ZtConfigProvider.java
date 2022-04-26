package config;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.FieldReference;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZtConfigProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        System.out.println("addCompletions");

        final PsiElement psiElement = completionParameters.getPosition();
        System.out.println("psiElement: " + psiElement.getText());
        final Project project = psiElement.getProject();
        System.out.println("project: " + project.getName());

        if(psiElement.getParent() instanceof FieldReference) {
            System.out.println("fieldReference");
            System.out.println(psiElement.getParent());
            System.out.println(((FieldReference) psiElement.getParent()).getName());
            System.out.println(psiElement.getParent().getText());
            String signature = ((FieldReference) psiElement.getParent()).getSignature();
            System.out.println("signature = " + signature);

            // Search ZT Config object
            String fieldPath = getConfigFieldPath(signature);

            System.out.println(((FieldReference) psiElement.getParent()).getCanonicalText());
            if (fieldPath.equals("G.Con")) {
                // Root elements
                completionResultSet.addElement(LookupElementBuilder.create("shop"));
                completionResultSet.addElement(LookupElementBuilder.create("search"));
                // TODO etc.
                // TODO get root elements from config file
            } else {
                // Folded elements
                completionResultSet.addElement(LookupElementBuilder.create("Hello"));
                // TODO get folded elements from config file
            }
        }
    }

    @Nullable
    private String getConfigFieldPath(String signature) {
        String fieldPath = null;
        int appIndex = signature.indexOf("G.Con");
        if(appIndex != -1){
            String origSign = signature.substring(appIndex);
            int endIndex = origSign.indexOf("|");
            if(endIndex != -1) {
                origSign = origSign.substring(0, endIndex);
            }
            int fieldIndex = origSign.lastIndexOf(".");
            origSign = origSign.substring(0, fieldIndex);
            System.out.println("origSign = " + origSign);
            fieldPath = origSign.replace("G.Con.", "");
            System.out.println("fieldPath = " + fieldPath);
        }
        return fieldPath;
    }
}
