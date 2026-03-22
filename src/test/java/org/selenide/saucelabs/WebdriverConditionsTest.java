package org.selenide.saucelabs;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.title;
import static com.codeborne.selenide.WebDriverConditions.*;

public class WebdriverConditionsTest {
  @Test
  void search() {
    open("https://selenide.org");
    $(".main-menu-pages").find(byText("Users")).click();

    webdriver().shouldHave(url("https://selenide.org/users.html"));
    webdriver().shouldHave(urlStartingWith("https://selenide.org"));
    webdriver().shouldHave(urlContaining("selenide.org/users"));
    webdriver().shouldHave(currentFrameUrl("https://selenide.org/users.html"));
    webdriver().shouldHave(numberOfWindows(1));
    webdriver().shouldHave(title("Selenide users"));
    webdriver().shouldNotHave(cookie("session", "sid-12345"));
    webdriver().shouldNotHave(cookie("session"));
  }
}
