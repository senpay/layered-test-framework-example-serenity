package gmail.alexspush.test;

import java.util.List;

import net.thucydides.core.annotations.Step;

/**
 * Created by Alexander Pushkarev.
 * 6.2.18
 *
 */
public interface ITodoCompositeSteps {

    @Step
    void userCreatedTodoItem(String todoItemName);

    @Step
    void userCompletedTodoItem(String todoItemName);

    @Step
    List<String> userCreatedNumberOfItems(int numberOfItemsCreated);

    @Step
    List<String> userCompletedNumberOfItems(List<String> todoItems, int numberOfItemsCompleted);

}
