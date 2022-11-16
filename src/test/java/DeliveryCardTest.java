import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        closeWebDriver();
    }

    @Test
    void validTest() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Магас");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79113456278");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(exactText("Успешно! " + "Встреча успешно забронирована на " + date), Duration.ofSeconds(15)).shouldBe(exist);

    }

    @Test
    void invalidCity() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Ингушетия");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79113456278");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"), Duration.ofSeconds(4)).shouldBe(exist);

    }

    @Test
    void invalidData() {
        String date = generateDate(2);
        $("[data-test-id='city'] input").setValue("Магас");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79113456278");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"), Duration.ofSeconds(5)).shouldBe(exist);

    }

    @Test
    void invalidName() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Магас");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Petrov Ivan");
        $("[data-test-id='phone'] input").setValue("+79113456278");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(8)).shouldBe(exist);

    }

    @Test
    void invalidPhone() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Магас");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("0000");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(8)).shouldBe(exist);

    }

    @Test
    void checkbox() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Магас");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79118778905");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"), Duration.ofSeconds(8)).shouldBe(exist);

    }

    @Test
    void emptyCity() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79113456278");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(4)).shouldBe(exist);

    }

    @Test
    void emptyName() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Магас");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79113456278");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(8)).shouldBe(exist);

    }

    @Test
    void emptyPhone() {
        String date = generateDate(3);
        $("[data-test-id='city'] input").setValue("Магас");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(8)).shouldBe(exist);

    }


}
