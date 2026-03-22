package org.selenide.saucelabs;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.LocalStorageConditions.item;
import static com.codeborne.selenide.LocalStorageConditions.itemWithValue;
import static com.codeborne.selenide.Selenide.localStorage;
import static com.codeborne.selenide.Selenide.open;

public class LocalStorageTest {
  @Test
  void canCheckLocalStorage() {
    open("https://selenide.org/test-page");
    localStorage().shouldNotHave(item("event"));

    localStorage().setItem("event", "Testμ 2024");
    localStorage().shouldHave(item("event"));
    localStorage().shouldHave(itemWithValue("event", "Testμ 2024"));
  }
}
