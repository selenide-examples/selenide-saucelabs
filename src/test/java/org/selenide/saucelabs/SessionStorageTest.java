package org.selenide.saucelabs;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sessionStorage;
import static com.codeborne.selenide.SessionStorageConditions.item;
import static com.codeborne.selenide.SessionStorageConditions.itemWithValue;

public class SessionStorageTest {
  @Test
  void canCheckSessionStorage() {
    open("https://selenide.org/test-page");
    sessionStorage().shouldNotHave(item("event"));

    sessionStorage().setItem("event", "Testμ 2024");
    sessionStorage().shouldHave(item("event"));
    sessionStorage().shouldHave(itemWithValue("event", "Testμ 2024"));
  }
}
