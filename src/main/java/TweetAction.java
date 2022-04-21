import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class TweetAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {

        // Получить выделение в редакторе
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        String selectedText = editor.getSelectionModel().getSelectedText();
        Messages.showMessageDialog("Hello world " + selectedText, "Tweet Action", Messages.getInformationIcon());
        // закодировать
        // склеить url твитера и закодированнуую часть
        // перейти  по урлу
    }

    @Override
    public boolean isDumbAware() {
        return false;
    }
}
