package com.topjava.graduation;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMatcher<T> {
    public static final int NOT_FOUND = 1;
    private final String[] fieldsToIgnore;

    private TestMatcher(String... fieldsToIgnore) {
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public static <T> TestMatcher<T> usingIgnoringFieldsComparator(String... fieldsToIgnore) {
        return new TestMatcher<>(fieldsToIgnore);
    }

    public void assertMatch(T actual, T expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
    }
}
