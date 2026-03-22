package org.selenide.saucelabs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class AnnotatedPageObjectTest {
  @BeforeEach
  void setUp() {
    open("https://selenide.org/users.html");
  }

  @Test
  void showsAllKnownSelenideUsers() {
    SelenideUsersPage page = page();
    page.users.shouldHave(size(1));

    page.showAll.click();
    page.users.shouldHave(sizeGreaterThan(40));
  }

  @Test
  void canFilterByTag() {
    SelenideUsersPage page = page();
    page.filterByTag("usa");
    page.users.shouldHave(sizeLessThan(40));
  }

  private static class SelenideUsersPage {
    @FindBy(css = "#selenide-users .user:not(.hidden)")
    ElementsCollection users;

    @FindBy(css = "#user-tags .reset-filter")
    SelenideElement showAll;

    @FindBy(css = "#user-tags .tag")
    ElementsCollection userTags;

    public void filterByTag(String tag) {
      userTags.stream()
        .filter(el -> el.getText().equals(tag))
        .findAny().orElseThrow(() -> new AssertionError("User tag not found: " + tag))
        .click();
    }
  }
}
