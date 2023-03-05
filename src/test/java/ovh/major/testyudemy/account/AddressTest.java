package ovh.major.testyudemy.account;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AddressTest {

    @ParameterizedTest
    @CsvSource({"Fabryczna, 10", "a to Kościuszki, 31A/1", "Armii Krajowej, 13", "'Romka, Tomka, Atomka', 45"})
    void givenAddressesShouldNotBeEmptyAndProperNames(String street, String number) {
        assertThat(street, notNullValue());
        assertThat(street, containsString("a"));
        assertThat(number, notNullValue());
        assertThat(number.length(), lessThan(8));

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addresses.csv")
    void addressesFromFileShouldNotBeEmptyAndProperNames(String street, String number) {
        assertThat(street, notNullValue());
        assertThat(street, containsString("a"));
        assertThat(number, notNullValue());
        assertThat(number.length(), lessThan(8));

    }
}
