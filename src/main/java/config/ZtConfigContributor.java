package config;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.psi.elements.FieldReference;

public class ZtConfigContributor extends CompletionContributor {
    public ZtConfigContributor() {
        System.out.println("ZtConfigContributor");
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withParent(FieldReference.class), new ZtConfigProvider());
    }
}
