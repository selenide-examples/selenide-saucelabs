package org.selenide.saucelabs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public final class DropdownTest {
  @BeforeEach
  void setUp() {
    open("https://selenide.org/test-page/dropdown.html");
  }

  @Test
  void canSelectOptionByText() {
    $("#hero").selectOption("Sarah Connor");
    $("#hero").getSelectedOption().shouldHave(value("sarah-connor"));
    $("#hero-summary").shouldHave(text("Your hero is: Sarah Connor (id: sarah-connor)"));
  }

  @Test
  void canSelectOptionBySubstring() {
    $("#hero").selectOptionContainingText("hn Mc");
    $("#hero").getSelectedOption().shouldHave(text("John McClane"));
    $("#hero-summary").shouldHave(text("Your hero is: John McClane (id: john-mcclane)"));
  }

  @Test
  void canSelectOptionByIndex() {
    $("#hero").selectOption(3);
    $("#hero-summary").shouldHave(text("Your hero is: Ellen Ripley (id: ellen-ripley)"));
  }

  @Test
  void canSelectOptionByValue() {
    $("#hero").selectOptionByValue("samwise-gamgee");
    $("#hero-summary").shouldHave(text("Your hero is: Samwise Gamgee (id: samwise-gamgee)"));
  }
}
