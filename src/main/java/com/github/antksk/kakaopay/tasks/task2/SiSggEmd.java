package com.github.antksk.kakaopay.tasks.task2;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

import static com.github.antksk.kakaopay.tasks.task2.SiSggEmd.RegExString.REGEX_ALL_SI_SGG;
import static com.github.antksk.kakaopay.tasks.task2.SiSggEmd.RegExString.REGEX_ALL_SI_SGG_EMD;
import static com.github.antksk.kakaopay.tasks.task2.SiSggEmd.RegExString.REGEX_EMD;
import static com.github.antksk.kakaopay.tasks.task2.SiSggEmd.RegExString.REGEX_EXP_SI_SGG;
import static com.github.antksk.kakaopay.tasks.task2.SiSggEmd.RegExString.REGEX_SGG;
import static com.github.antksk.kakaopay.tasks.task2.SiSggEmd.RegExString.REGEX_SI;

@Slf4j
public enum SiSggEmd {

    SI(Pattern.compile(REGEX_SI)),
    SGG(Pattern.compile(REGEX_SGG)),
    EMD(Pattern.compile(REGEX_EMD)),
    ALL_SI_SGG_EMD(Pattern.compile(REGEX_ALL_SI_SGG_EMD)),
    ALL_SI_SGG(Pattern.compile(REGEX_ALL_SI_SGG)),
    EXP_SI_SGG(Pattern.compile(REGEX_EXP_SI_SGG)),
    ALL_SI(Pattern.compile(RegExString.REGEX_ALL_SI))
    ;
    private final Pattern pattern;

    SiSggEmd(Pattern pattern) {
        this.pattern = pattern;
    }


    public boolean match(String siSggEmd){
        return pattern.matcher(siSggEmd).matches();
    }

    public String formalRegion(String siSggEmd){
        final Matcher matcher = pattern.matcher(siSggEmd);

        String formalRegion = "";
        if(matcher.find()) {
            formalRegion = matcher.group("formalRegion");
        }

        return formalRegion;
    }

    private static final ImmutableSet<SiSggEmd> formalRegions = Sets.immutableEnumSet(
        SiSggEmd.ALL_SI_SGG_EMD,
        SiSggEmd.ALL_SI_SGG,
        SiSggEmd.EXP_SI_SGG,
        SiSggEmd.ALL_SI
    );

    public static Optional<String> getFormalRegion(final String region){

        // log.debug("getFormalRegion : {}, {}", region, StringUtils.isEmpty(region));
        if(StringUtils.isEmpty(region)) return Optional.empty();

        final String r = removeSpecialString(region);
        return formalRegions.stream()
                         .filter(e -> e.match(r))
                         .findFirst()
                         .map(e->e.formalRegion(r))
        ;
    }

    private static final Pattern removeSpecialStringPattern = Pattern.compile("(?:[, ]+$)");
    public static String removeSpecialString(String str){
        if(StringUtils.isEmpty(str)) return StringUtils.EMPTY;
        final Matcher matcher = removeSpecialStringPattern.matcher(str);

        final StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb, "");
        }
        matcher.appendTail(sb);

        return sb.toString();

    }

    public static class RegExString {
        public static final String REGEX_FORMAL_REGION = "(?<formalRegion>%s)";
        public static final String REGEX_SI = "\\S+(?:(?:특별)?시|도)";
        public static final String REGEX_SGG = "\\S+(?:시|군|구)";
        public static final String REGEX_EMD = "\\S+(?:읍|면|동)";
        public static final String REGEX_ALL_SI = String.format(REGEX_FORMAL_REGION, "^" + REGEX_SI) + " .*";
        public static final String REGEX_ALL_SI_SGG = String.format(REGEX_FORMAL_REGION, "^" + REGEX_SI + " " + REGEX_SGG) +"(?: .*)?";
        public static final String REGEX_EXP_SI_SGG = String.format(REGEX_FORMAL_REGION, "^" + REGEX_SI + " \\S{2,4}") + "(?: .*)?";
        public static final String REGEX_ALL_SI_SGG_EMD = String.format(REGEX_FORMAL_REGION, "^"+REGEX_SI + " " + REGEX_SGG + " " + REGEX_EMD) + "(?: .*)?";
    }

}
