package config;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
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
        final FileBasedIndex fileBasedIndex = FileBasedIndex.getInstance();

        final PsiElement psiElement = completionParameters.getPosition();

        final Project project = psiElement.getProject();
        final GlobalSearchScope scope = GlobalSearchScope.projectScope(project);

        if (psiElement.getParent() instanceof FieldReference) {
            String signature = ((FieldReference) psiElement.getParent()).getSignature();

            // Search ZT Config object
            String fieldPath = getConfigFieldPath(signature);

            String field = fieldPath;
            if (field != null) {
                fileBasedIndex.processAllKeys(ConfigIndex.identity, key -> {
                    if (field.equals("G.Config")) {
                        if (key.indexOf(".") != -1) {
                            return true;
                        }
                        // TODO Must add a value for key
                        //  .withTypeText(value, true)
                        LookupElementBuilder lookupElement = LookupElementBuilder.create(key);
                        completionResultSet.addElement(lookupElement);
                        return true;
                    } else if (!key.equals(field)) {
                        return true;
                    }

                    List<List<String>> values = fileBasedIndex.getValues(ConfigIndex.identity, key, scope);
                    if (values.size() > 0) {
                        List<String> actualValues = values.get(0);
                        for (var value : actualValues) {
                            // TODO Must add a value for key
                            //  .withTypeText(value, true)
                            LookupElementBuilder lookupElement = LookupElementBuilder.create(value);
                            completionResultSet.addElement(lookupElement);
                        }
                    }
                    return true;
                }, project);
            }
        }
    }

    @Nullable
    private String getConfigFieldPath(String signature) {
        String fieldPath = null;
        signature = signature.replaceAll("[\\(\\)]", "");
        int appIndex = signature.indexOf("G.Config");
        if (appIndex != -1) {
            String origSign = signature.substring(appIndex);
            int endIndex = origSign.indexOf("|");
            if (endIndex != -1) {
                origSign = origSign.substring(0, endIndex);
            }
            int fieldIndex = origSign.lastIndexOf(".");
            origSign = origSign.substring(0, fieldIndex);
            fieldPath = origSign.replace("G.Config.", "");
        }

        return fieldPath;
    }
}
