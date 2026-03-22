package org.selenide.saucelabs;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.remote.CapabilityType.ENABLE_DOWNLOADS;

public class TestSetup implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
  private static final Logger log = LoggerFactory.getLogger(TestSetup.class);
  private final String username = System.getenv("SAUCE_USERNAME");
  private final String accessKey = System.getenv("SAUCE_ACCESS_KEY");

  @Override
  public void beforeAll(ExtensionContext context) {
    log.info("Start tests {}", context.getDisplayName());
    setupWebdriver(context);
  }

  @Override
  public void afterAll(ExtensionContext context) {
    log.info("Finished tests {}", context.getDisplayName());
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    log.info("Start test {}.{}", context.getRequiredTestClass().getSimpleName(), context.getDisplayName());

    setupWebdriver(context);
    open();
  }

  private void setupWebdriver(ExtensionContext context) {
    Configuration.remote = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
    Configuration.browserCapabilities.setCapability(ENABLE_DOWNLOADS, true);
    Configuration.browserCapabilities.setCapability("webSocketUrl", true);
    Configuration.browserCapabilities.setCapability("sauce:options", Map.of(
      "source", "selenide-examples:selenide-saucelabs:main",
      "userName", username,
      "accessKey", accessKey,
      "seleniumVersion", "4.39.0",
      "name", context.getRequiredTestClass().getName() + '.' + context.getDisplayName()
    ));

    log.debug("Setup webdriver: remoteUrl='{}', username='{}', accessKey='{}'", Configuration.remote,
      username.replaceAll(".", "*"), accessKey.replaceAll(".", "*"));
  }

  @Override
  public void afterEach(ExtensionContext context) {
    log.info("Finish test {}.{}", context.getRequiredTestClass().getSimpleName(), context.getDisplayName());

    if (WebDriverRunner.hasWebDriverStarted()) {
      printResults(context);
      if (context.getExecutionException().isPresent()) {
        executeJavaScript("sauce:job-result=failed");
      } else {
        executeJavaScript("sauce:job-result=passed");
      }
      closeWebDriver();
    }
  }

  private void printResults(ExtensionContext context) {
    SessionId sessionId = ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getSessionId();
    String jobName = context.getRequiredTestClass().getName() + '.' + context.getDisplayName();
    String sauceReporter =
      String.format("SauceOnDemandSessionID=%s job-name=%s", sessionId, jobName);
    String sauceTestLink = String.format("Test Job Link: https://app.saucelabs.com/tests/%s", sessionId);
    log.info("{}\n{}\n", sauceReporter, sauceTestLink);
  }
}
