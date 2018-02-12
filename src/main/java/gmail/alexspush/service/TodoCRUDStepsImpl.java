package gmail.alexspush.service;

import gmail.alexspush.page.SelenideMainPage;
import net.thucydides.core.annotations.Step;

/**
 * Created by Alexander Pushkarev on 9.2.18.
 */
public class TodoCRUDStepsImpl {

    private IMainPage mainPage = SelenideMainPage.INSTANCE;

    @Step
    public void userEntersTodoName(String todoName) {
        mainPage.setNewItemName(todoName);
        mainPage.hitEnter();
    }

    @Step
    public void userMarksItemAsComplete(String todoItemName) {
        mainPage.selectCheckBoxForItem(todoItemName);
    }

    @Step
    public void userDeletesItem(String todoItemName) {
        mainPage.clickDeleteButtonForItem(todoItemName);
    }

    @Step
    public void userUnMarksItemAsComplete(String todoItemName) {
        mainPage.unSelectCheckBoxForItem(todoItemName);
    }
}
