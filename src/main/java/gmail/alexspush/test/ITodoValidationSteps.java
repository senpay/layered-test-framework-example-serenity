package gmail.alexspush.test;

import java.util.List;

import net.thucydides.core.annotations.Step;

/**
 * Created by Alexander Pushkarev.
 * 6.2.18

 */
public interface ITodoValidationSteps {

    @Step
    void todoItemIsPresent(String todoName);

    @Step
    void todoItemMarkedCompeted(String todoName);

    @Step
    void todoItemIsNotPresent(String todoItemName);

    @Step
    void todoItemIsNotMarkedCompeted(String todoItemName);

    @Step
    void userDoesNotSeeItems(List<String> items);

    @Step
    void userSeesItems(List<String> items);
}
