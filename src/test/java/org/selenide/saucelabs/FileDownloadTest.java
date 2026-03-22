package org.selenide.saucelabs;

import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.DownloadOptions.using;
import static com.codeborne.selenide.FileDownloadMode.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.isChrome;
import static com.codeborne.selenide.WebDriverRunner.isEdge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

public class FileDownloadTest {
  @Test
  void downloadFile_using_httpGet() {
    checkDownload(HTTPGET);
  }

  @Test
  void downloadFile_using_cdp() {
    assumeThat(isChrome() || isEdge()).isTrue();
    checkDownload(CDP);
  }

  @Test
  void downloadFile_using_folder() {
    checkDownload(FOLDER);
  }

  @Test
  @Disabled("Need to use 'sauce connect' tool to make Selenide proxy available from remote browser")
  void downloadFile_using_proxy() {
    checkDownload(PROXY);
  }

  static void checkDownload(FileDownloadMode method) {
    open("https://selenide.org/test-page/download.html");
    File file = $(byText("hello-world.txt")).download(using(method).withExtension("txt").withTimeout(9999));
    assertThat(file).hasName("hello-world.txt");
    assertThat(file).content().isEqualTo("Hello, world!");
  }
}
