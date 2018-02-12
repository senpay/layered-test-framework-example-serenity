package gmail.alexspush.test;

import net.thucydides.core.annotations.Step;

/**
 * Created by Alexander Pushkarev.
 * 6.2.18
 */
public interface IGenericSteps {

    @Step
    void openApplication();

    @Step
    void closeApplication();
}
