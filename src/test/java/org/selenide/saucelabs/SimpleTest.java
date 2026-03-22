package org.selenide.saucelabs;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SimpleTest {
  @Test
  void search() {
    open("https://selenide.org");
    $(".main-menu-pages").find(byText("Users")).click();

    $$("#user-tags .tag").shouldHave(sizeGreaterThan(8));
    $$("#selenide-users .user:not(.hidden)").shouldHave(size(1));

    $("#user-tags").find(byTagAndText("a", "all")).click();
    $$("#selenide-users .user:not(.hidden)").shouldHave(sizeGreaterThan(80));

    $("#user-tags").find(byTagAndText("a", "ukraine")).click();
    $$("#selenide-users .user:not(.hidden)").shouldHave(sizeLessThan(80));

    $("#user-tags").find(byTagAndText("a", "healthcare")).click();
    $$("#selenide-users .user:not(.hidden)").shouldHave(sizeLessThan(20));
  }
}
