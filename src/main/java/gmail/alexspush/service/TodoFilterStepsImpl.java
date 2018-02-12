package gmail.alexspush.service;

import gmail.alexspush.page.SelenideMainPage;
import net.thucydides.core.annotations.Step;

/**
 * Created by Alexander Pushkarev on 10.2.18.
 */
public class TodoFilterStepsImpl {

    private IMainPage mainPage = SelenideMainPage.INSTANCE;

    @Step
    public void selectsCompletedFilter() {
        mainPage.clickCompletedLink();
    }

    @Step
    public void selectsAllFilter() {
        mainPage.clickAllLink();
    }

    @Step
    public void selectsActiveFilter() {
        mainPage.clickActiveLink();
    }
}
