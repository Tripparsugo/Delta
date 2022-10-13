import com.company.Delta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Function;

class TestDelta {
    final static String html1 = "<td align=left valign=top>" +
            "<SELECT NAME=\"op sys\" MULTIPLE SIZE=7>" +
            "<OPTION VALUE=\"All\">All<OPTION VALUE=\"Windows 3.1\">Windows 3.1"
            + "<OPTION VALUE=\"Windows 95\">Windows 95<OPTION VALUE=\"Windows 98\">"
            + "Windows 98<OPTION VALUE=\"Windows ME\">Windows ME<OPTION VALUE=\"Windows 2000\">"
            + "Windows 2000<OPTION VALUE=\"Windows NT\">Windows NT<OPTION VALUE=\"Mac System 7\">"
            + "Mac System 7<OPTION VALUE=\"Mac System 7.5\">Mac System 7.5<OPTION VALUE=\"Mac System 7.6.1\">"
            + "Mac System 7.6.1<OPTION VALUE=\"Mac System 8.0\">Mac System 8.0<OPTION VALUE=\"Mac System 8.5\">"
            + "Mac System 8.5<OPTION VALUE=\"Mac System 8.6\">Mac System 8.6<OPTION VALUE=\"Mac System 9.x\">Mac System 9.x"
            + "<OPTION VALUE=\"MacOS X\">MacOS X<OPTION VALUE=\"Linux\">Linux<OPTION VALUE=\"BSDI\">BSDI<OPTION VALUE=\"FreeBSD\">"
            + "FreeBSD<OPTION VALUE=\"NetBSD\">NetBSD<OPTION VALUE=\"OpenBSD\">OpenBSD<OPTION VALUE=\"AIX\">AIX<OPTION VALUE=\"BeOS\">"
            + "BeOS<OPTION VALUE=\"HP-UX\">HP-UX<OPTION VALUE=\"IRIX\">IRIX<OPTION VALUE=\"Neutrino\">Neutrino<OPTION VALUE=\"OpenVMS\">"
            + "OpenVMS<OPTION VALUE=\"OS/2\">OS/2<OPTION VALUE=\"OSF/1\">OSF/1<OPTION VALUE=\"Solaris\">Solaris<OPTION VALUE=\"SunOS\">"
            + "SunOS<OPTION VALUE=\"other\">other</SELECT>" + "</td>" + "<td align=left valign=top>" + "<SELECT NAME=\"priority\" MULTIPLE SIZE=7>"
            + "<OPTION VALUE=\"--\">--<OPTION VALUE=\"P1\">P1<OPTION VALUE=\"P2\">P2<OPTION VALUE=\"P3\">P3<OPTION VALUE=\"P4\">"
            + "P4<OPTION VALUE=\"P5\">P5</SELECT>" + "</td>" + "<td align=left valign=top>" + "<SELECT NAME=\"bug severity\" MULTIPLE SIZE=7>"
            + "<OPTION VALUE=\"blocker\">blocker<OPTION VALUE=\"critical\">critical<OPTION VALUE=\"major\">major<OPTION VALUE=\"normal\">"
            + "normal<OPTION VALUE=\"minor\">minor<OPTION VALUE=\"trivial\">trivial<OPTION VALUE=\"enhancement\">enhancement</SELECT></tr></table>";

    @Test
    void test1() {
        Function<String, Boolean> faultCheck = s -> s.matches(".*<.*ACCEPT.*>.*");
        Assertions.assertEquals( "<SELECT>", Delta.deltaSplit(html1, faultCheck));
    }

    @Test
    void test2() {
        Function<String, Boolean> faultCheck = s -> s.matches(".*windows[ ]*98.*");
        Assertions.assertEquals("windows 98", Delta.deltaSplit(html1, faultCheck));
    }

    @Test
    void test3() {
        Function<String, Boolean> faultCheck = s -> true;
        Assertions.assertEquals(Delta.deltaSplit(html1, faultCheck), "");
    }

    @Test
    void test4() {
        Function<String, Boolean> faultCheck = s -> false;
        Assertions.assertThrows(IllegalArgumentException.class, ()->Delta.deltaSplit("", faultCheck));
    }

    @Test
    void test5() {
        var expected =  new String[]{"12", "34", "34", "12"};
        var actual = Delta.partitionString("1234", 2);
        Assertions.assertArrayEquals( expected, actual);
    }

    @Test
    void test6() {
        var expected =  new String[]{"1", "234", "2", "134", "3", "124", "4", "123"};
        var actual = Delta.partitionString("1234", 4);
        Assertions.assertArrayEquals( expected, actual);
    }

    @Test
    void test7() {
        var expected =  new String[]{"1", "23", "2", "13", "3", "12"};
        var actual = Delta.partitionString("123", 3);
        Assertions.assertArrayEquals( expected, actual);
    }

    @Test
    void test8() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->Delta.partitionString("123", 4));
    }


}