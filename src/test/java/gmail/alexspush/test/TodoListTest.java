package gmail.alexspush.test;

import java.util.List;

import gmail.alexspush.service.GenericStepsImpl;
import gmail.alexspush.service.TodoCRUDStepsImpl;
import gmail.alexspush.service.TodoCompositeStepsImpl;
import gmail.alexspush.service.TodoFilterStepsImpl;
import gmail.alexspush.service.TodoValidationStepsImpl;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static gmail.alexspush.utils.TestUtils.generateItemName;
import static gmail.alexspush.utils.TestUtils.getListWithoutSublist;

/**
 * Created by Alexander Pushkarev.
 * 6.2.18
 */
@RunWith(SerenityRunner.class)
public class TodoListTest {


    //In order for Serenity to do injection for me I will have to use
    //Concrete classes here
    //So Interfaces not really needed if we use Serenity
    //DI mechanism
    @Steps
    GenericStepsImpl genericSteps;
    @Steps
    TodoCRUDStepsImpl todoCRUDSteps;
    @Steps
    TodoFilterStepsImpl todoFilterSteps;
    @Steps
    TodoValidationStepsImpl todoValidationSteps;
    @Steps
    TodoCompositeStepsImpl todoCompositeSteps;

    @Before
    public void setUp() {
        //Here I am not specifying which url to open - staging/production/local.
        //In general case, test should know nothing about it
        //There're some prominent exceptions, but they are out of scope of this example
        genericSteps.openApplication();
    }

    @After
    public void tearDown() {
        //Here I am not specifying which url to open - staging/production/local.
        //In general case, test should know nothing about it
        //There're some prominent exceptions, but they are out of scope of this example
        genericSteps.closeApplication();
    }

    /**
     * At this stage it is going to be simple scenario.
     * Let's assume thing we have on a business layer (which not yet exist at the moment) is called steps.
     *
     */
    @Test
    public void shouldBeAbleToAddNewTodoItem() {
        //Even with java-based test automation it never hurts to specify
        //What is given (i.e. things that put system into particular state)
        //What is an action under test
        //And where we do verify the outcome

        genericSteps.given();
        //Not much here - setUp() is almost enough
        //it would be nice to extract this name generation to some
        //getItemName() method, but that is boring thing to do, so I won't do it
        final String todoItemName = generateItemName();

        genericSteps.when();
        todoCRUDSteps.userEntersTodoName(todoItemName);

        genericSteps.then();
        //Here we're making assumption that there were no such item before.
        //It can be easily addressed if we generate new name for each test
        //I will not add this implementation here as this is pretty straight-forward thing to do
        todoValidationSteps.todoItemIsPresent(todoItemName);
    }

    /**
     * I am using technique called "atomic cases"
     * It is probably most known (and most under-used) technique.
     *
     * Other approach would be to do "E2E flow" UI scenario.
     *
     * "E2E flow" UI scenarios are good as a local optimization technique
     * but lame as a communication mechanism
     *
     * Even though I am not using vanila BDD here, I think test should
     * provide information and that should be a priority.
     * Atomic test are good as they mostlikely have only one reason to failure,
     * Which should make it way easier to find failure source
     *
     * Speed, efficiency and other BS goes after that (for me)
     *
     */
    @Test
    public void shouldBeAbleToMarkTodoItemAsCompleted() {
        genericSteps.given();
        final String todoItemName = generateItemName();
        //would be nice to reference prev. test here, wouldn't it?
        todoCompositeSteps.userCreatedTodoItem(todoItemName);

        genericSteps.when();
        todoCRUDSteps.userMarksItemAsComplete(todoItemName);

        genericSteps.then();
        //Here we're making assumption that there were no such item before.
        //It can be easily addressed if we generate new name for each test
        //I will not add this implementation here as this is pretty straight-forward thing to do
        todoValidationSteps.todoItemMarkedCompeted(todoItemName);
    }

    /**
     * Let's see if it is clear to understand the test intent without my
     * comments
     *
     */
    @Test
    public void shouldBeAbleToUnMarkTodoItemAsCompleted() {
        genericSteps.given();
        final String todoItemName = generateItemName();
        todoCompositeSteps.userCreatedTodoItem(todoItemName);
        todoCompositeSteps.userCompletedTodoItem(todoItemName);

        genericSteps.when();
        todoCRUDSteps.userUnMarksItemAsComplete(todoItemName);

        genericSteps.then();
        todoValidationSteps.todoItemIsNotMarkedCompeted(todoItemName);
    }


    /**
     * Let's see if it is clear to understand the test intent without my
     * comments
     *
     */
    @Test
    public void shouldBeAbleToDeleteTodoItem() {
        genericSteps.given();
        final String todoItemName = generateItemName();
        todoCompositeSteps.userCreatedTodoItem(todoItemName);

        genericSteps.when();
        todoCRUDSteps.userDeletesItem(todoItemName);

        genericSteps.then();
        todoValidationSteps.todoItemIsNotPresent(todoItemName);
    }

    /**
     * This guys would be actually very good to be a parametrized test.
     * Didn't I say I am a lazy brat?
     *
     * Parametrized tests are too easy to implement by my standards,
     * so I will not bother with it here
     *
     * Also, from communication stand-point parametrised tests _may_ be
     * confusing to read (although that is not always the case)
     */
    @Test
    public void shouldShowOnlyActiveItemsIfActiveFilterApplied() {
        //At this stage we get into a gray area - "given" steps are
        //becoming more complicated that test itself.
        //I would consider this as a clear indicator that
        //This "state preparation" logic should be done outside the UI
        genericSteps.given();
        final int numberOfItemsCreated = 6;
        final int numberOfItemsCompleted = 4;
        final List<String> todoItems = todoCompositeSteps.userCreatedNumberOfItems(numberOfItemsCreated);
        final List<String> completedItems = todoCompositeSteps.userCompletedNumberOfItems(todoItems, numberOfItemsCompleted);
        final List<String> activeItems = getListWithoutSublist(todoItems, completedItems);

        genericSteps.when();
        todoFilterSteps.selectsActiveFilter();

        genericSteps.then();
        todoValidationSteps.userSeesItems(activeItems);
        todoValidationSteps.userDoesNotSeeItems(completedItems);
    }

    /**
     * This guys would be actually very good to be a parametrized test.
     * Same as previous.
     */
    @Test
    public void shouldShowOnlyCompletedItemsIfCompletedFilterApplied() {
        genericSteps.given();
        final int numberOfItemsCreated = 5;
        final int numberOfItemsCompleted = 2;
        final List<String> todoItems = todoCompositeSteps.userCreatedNumberOfItems(numberOfItemsCreated);
        final List<String> completedItems = todoCompositeSteps.userCompletedNumberOfItems(todoItems, numberOfItemsCompleted);
        final List<String> activeItems = getListWithoutSublist(todoItems, completedItems);

        genericSteps.when();
        todoFilterSteps.selectsCompletedFilter();

        genericSteps.then();
        todoValidationSteps.userSeesItems(completedItems);
        todoValidationSteps.userDoesNotSeeItems(activeItems);
    }

    /**
     * This guys would be actually very good to be a parametrized test.
     * Same as previous.
     */
    @Test
    public void shouldShowAllItemsIfNoFilterApplied() {
        genericSteps.given();
        final int numberOfItemsCreated = 5;
        final int numberOfItemsCompleted = 2;
        final List<String> todoItems = todoCompositeSteps.userCreatedNumberOfItems(numberOfItemsCreated);
        todoCompositeSteps.userCompletedNumberOfItems(todoItems, numberOfItemsCompleted);

        genericSteps.when();
        todoFilterSteps.selectsAllFilter();

        genericSteps.then();
        todoValidationSteps.userSeesItems(todoItems);
    }


    /**
     * This guys would be actually very good to be a parametrized test.
     * Same as previous.
     */
    @Test
    public void failingTestForHtmlReportCheck() {
        genericSteps.given();
        final int numberOfItemsCreated = 5;
        final List<String> todoItems = todoCompositeSteps.userCreatedNumberOfItems(numberOfItemsCreated);

        genericSteps.when();

        genericSteps.then();
        todoValidationSteps.userDoesNotSeeItems(todoItems);
    }
}
