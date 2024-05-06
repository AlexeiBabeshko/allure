package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import java.io.IOException;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
public class CardDeliveryTest {

    String citySelector = "[data-test-id=city] input";
    String dateSelector = "[data-test-id=date] input";
    String nameSelector = "[data-test-id=name] input";
    String phoneSelector = "[data-test-id=phone] input";
    String agreementSelector = "[data-test-id=agreement] span.checkbox__box";
    String buttonSelector = "button .button__text";
    String notificationTitleSelector = "[data-test-id=success-notification] .notification__title";
    String notificationContentSelector = "[data-test-id=success-notification] .notification__content";
    String notificationRePlanTitleSelector = "[data-test-id=replan-notification] .notification__title";
    String notificationRePlanButton = "[data-test-id=replan-notification] button";

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan")
    void shouldSuccessfulPlanAndRePlanMeeting() throws IOException {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $(citySelector).setValue(validUser.getCity());
        $(dateSelector).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(dateSelector).setValue(firstMeetingDate);
        $(nameSelector).setValue(validUser.getName());
        $(phoneSelector).setValue(validUser.getPhone());
        $(agreementSelector).click();
        $(buttonSelector).shouldHave(text("Запланировать")).click();

        $(notificationTitleSelector).should(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $(notificationContentSelector).shouldHave(visible, text("Встреча успешно запланирована на " + firstMeetingDate));

        $(dateSelector).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(dateSelector).setValue(secondMeetingDate);
        $(buttonSelector).shouldHave(text("Запланировать")).click();
        $(notificationRePlanTitleSelector).should(visible).shouldHave(text("Необходимо подтверждение"));
        $(notificationRePlanButton).should(visible).shouldHave(text("Перепланировать")).click();

        $(notificationTitleSelector).should(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $(notificationContentSelector).shouldHave(visible, text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
