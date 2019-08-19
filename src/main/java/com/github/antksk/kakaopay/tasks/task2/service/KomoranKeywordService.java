package com.github.antksk.kakaopay.tasks.task2.service;

import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.extern.slf4j.Slf4j;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

@Slf4j
@Service
public class KomoranKeywordService implements KeywordService {
    private final Komoran komoran;
    /**
     * @ref : https://docs.komoran.kr/firststep/postypes.html
     */
    private static final Set<String> poss = Sets.newHashSet(
        "VV" /*동사*/
        , "NNP" /*고유명사*/
        , "NNG" /*일반명사*/
        , "NNB" /*의존명사*/
    );

    public KomoranKeywordService(Komoran komoran) {
        this.komoran = komoran;
    }


    @Override
    public ImmutableSet<String> analyze(String sentence){
        if(StringUtils.isEmpty(sentence)) return ImmutableSet.copyOf(Collections.emptySet());

        final KomoranResult analyze = komoran.analyze(sentence);
        return analyze.getTokenList().stream()
//                      .peek(e->log.debug("{}", e))
                      .filter(t->poss.contains(t.getPos()))
                      .map(Token::getMorph)
               .collect(toImmutableSet())
        ;
    }
}
