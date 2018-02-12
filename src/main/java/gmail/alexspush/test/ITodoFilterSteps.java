package gmail.alexspush.test;

import net.thucydides.core.annotations.Step;

/**
 * Created by Alexander Pushkarev.
 * 6.2.18
 */
public interface ITodoFilterSteps {

    @Step
    void selectsCompletedFilter();

    @Step
    void selectsAllFilter();

    @Step
    void selectsActiveFilter();

}
