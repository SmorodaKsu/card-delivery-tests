import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void openPage() {
        Configuration.headless = true; // опционально
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequestSuccessfully() {
        String planningDate = generateDate(3);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79998887766");
        $("[data-test-id=agreement]").click();
        $$("button").findBy(text("Забронировать")).click();

        $("[data-test-id=notification]").should(appear, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planningDate));
    }
}
