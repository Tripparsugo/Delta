package com.company;

import java.util.function.Function;

public class Delta {

    public static String deltaSplit(String in, Function<String, Boolean> faultOracle) {
        return deltaSplitHelp(in, faultOracle, 2);
    }

    private static String deltaSplitHelp(String in, Function<String, Boolean> faultOracle, final int n) {
        var partitions = partitionString(in, n);
        int i = 0;
        while (i < n) {
            String cut = partitions[i * 2];
            String rest = partitions[i * 2 + 1];

            if (faultOracle.apply(rest)) {
                return deltaSplitHelp(rest, faultOracle, Math.max(n - 1, 2));
            }

            if (faultOracle.apply(cut)) {
                return deltaSplitHelp(cut, faultOracle, 2);
            }


            i++;
        }
        return n * 2 > in.length() ? in : deltaSplitHelp(in, faultOracle, n * 2);
    }

    public static String[] partitionString(String s, int n) {
        if (n > s.length() || n < 2) {
            throw new IllegalArgumentException("String.length must be <= n");
        }
        String[] partitions = new String[n * 2];
        final int cutLength = s.length() / n;
        for (int i = 0; i < n; i++) {
            final int cutStart = i * s.length() / n;
            final int cutEnd = Math.min(cutStart + cutLength, s.length());
            final String cut = s.substring(cutStart, cutEnd);
            final String rest = s.substring(0, cutStart) + s.substring(cutEnd);
            partitions[i * 2] = cut;
            partitions[i * 2 + 1] = rest;
        }
        return partitions;


    }

    public static void main(String[] args) {
        Function<String, Boolean> faultOracle = s -> s.matches(".*<.*SELECT.*>.*");
        final String html1 = "<td align=left valign=top>" +
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

        System.out.println(deltaSplit(html1, faultOracle));
    }


}
