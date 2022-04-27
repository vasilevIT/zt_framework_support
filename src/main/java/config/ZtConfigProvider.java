package config;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ProcessingContext;
import com.intellij.util.indexing.FileBasedIndex;
import com.jetbrains.php.lang.psi.elements.FieldReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZtConfigProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        System.out.println("addCompletions");
        final FileBasedIndex fileBasedIndex = FileBasedIndex.getInstance();


        final PsiElement psiElement = completionParameters.getPosition();

        // TODO remove
//        System.out.println("psiElement: " + psiElement.getText());
        final Project project = psiElement.getProject();
        final GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
        System.out.println("project: " + project.getName());

        if (psiElement.getParent() instanceof FieldReference) {
//            TODO remove
//            System.out.println("fieldReference");
//            System.out.println(psiElement.getParent());
//            System.out.println(((FieldReference) psiElement.getParent()).getName());
//            System.out.println(psiElement.getParent().getText());
            String signature = ((FieldReference) psiElement.getParent()).getSignature();
//            TODO remove
//            System.out.println("signature = " + signature);

            // Search ZT Config object
            String fieldPath = getConfigFieldPath(signature);

            System.out.println(((FieldReference) psiElement.getParent()).getCanonicalText());
//            TODO remove
//            if (fieldPath.equals("G.Con")) {
//                // Root elements
//                completionResultSet.addElement(LookupElementBuilder.create("shop"));
//                completionResultSet.addElement(LookupElementBuilder.create("search"));
//                // TODO etc.
//                // TODO get root elements from config file
//            } else {
//                // Folded elements
//                completionResultSet.addElement(LookupElementBuilder.create("Hello"));
//                // TODO get folded elements from config file
//            }


            String field = fieldPath;
            if (field != null) {
                fileBasedIndex.processAllKeys(ConfigIndex.identity, key -> {
//                    TODO remove
                    System.out.println("fileBasedIndex, field " + field);
                    System.out.println("fileBasedIndex, key " + key);

                    if (field.equals("G.Con")) {
                        if (key.indexOf(".") != -1) {
                            return true;
                        }
                    } else if (!key.equals(field)) {
                        return true;
                    }
//                    TODO remove
//                    System.out.println("fileBasedIndex, key accepted " + key);

                    List<String> values = fileBasedIndex.getValues(ConfigIndex.identity, key, scope);
//                    TODO remove
//                    System.out.println("fileBasedIndex, values, " + values);

                    // TODO if root level, show only 1-level keys
                    // TODO fillter by field
                    // TODO add to lookup only children
                    if (values.size() > 0) {
//                        TODO remove
//                        System.out.println("fileBasedIndex, values[0], " + values.get(0));
                        LookupElementBuilder lookupElement = LookupElementBuilder.create(key).withTypeText(values.get(0), true).withIcon(AllIcons.Nodes.Class);

                        completionResultSet.addElement(lookupElement);
                    }
                    return true;
                }, project);
            }
        }
    }

    @Nullable
    private String getConfigFieldPath(String signature) {
        String fieldPath = null;
        int appIndex = signature.indexOf("G.Con");
        if (appIndex != -1) {
            String origSign = signature.substring(appIndex);
            int endIndex = origSign.indexOf("|");
            if (endIndex != -1) {
                origSign = origSign.substring(0, endIndex);
            }
            int fieldIndex = origSign.lastIndexOf(".");
            origSign = origSign.substring(0, fieldIndex);
            System.out.println("origSign = " + origSign);
            fieldPath = origSign.replace("G.Con.", "");
            System.out.println("fieldPath = " + fieldPath);
        }

        fieldPath = fieldPath.replaceAll("[\\(\\)]", "");

        return fieldPath;
    }
}
