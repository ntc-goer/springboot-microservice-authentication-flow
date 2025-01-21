package util;

import ntcgoer.sharingmodule.util.HashUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HashUtilTest {
    private HashUtil hashUtil;

    @BeforeEach
    public void setUp() {
        hashUtil = new HashUtil();
    }

    @Test
    public void whenHashString_thenHashedStringReturned(){
        String testStr = "123456789";
        String hashedStr = hashUtil.encode(testStr);
        assertThat(hashedStr).isNotNull().isNotEmpty();
    }

    @Test
    public void whenHashSameString_thenHashedStringDifferent() {
        String testStr = "123456789";
        String hashedStr1 = hashUtil.encode(testStr);
        String hashedStr2 = hashUtil.encode(testStr);
        assertThat(hashedStr1).isNotEqualTo(hashedStr2);
    }

    @Test
    public void whenHashString_thenHashedStringMatchToString() {
        String testStr = "123456789";
        String hashedStr = hashUtil.encode(testStr);
        assertThat(hashUtil.matches(testStr, hashedStr)).isTrue();
    }

    @Test
    public void whenNullString_thenThrowException(){
        assertThatThrownBy(() -> hashUtil.encode(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenEmptyString_thenStringReturned(){
        assertThat(hashUtil.encode("")).isNotNull().isNotEmpty();
    }

}
