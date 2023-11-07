package lotto.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lotto.dto.ResultDto;
import lotto.embedded.LottoResult;
import lotto.model.Lotto;
import org.junit.jupiter.api.Test;

class LottoControllerTest {

    private final LottoController lottoController = new LottoController();

    @Test
    public void 개별_결과_확인(){
        //given
        Lotto winning = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 16));
        int bonusNumber = 17;
        int totalPrice = 6000;
        Lotto first = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 16));
        Lotto second = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 17));
        Lotto third = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 18));
        Lotto fourth = new Lotto(Arrays.asList(11, 12, 13, 14, 18, 19));
        Lotto fifth = new Lotto(Arrays.asList(11, 12, 13, 18, 19, 20));
        Lotto none = new Lotto(Arrays.asList(11, 12, 18, 19, 20, 21));
        List<Lotto> lottos = Arrays.asList(first, second, third, fourth, fifth, none);

        List<LottoResult> results = new ArrayList<>();
        //when
        for(Lotto l: lottos){
            results.add(lottoController.calculateLottoResult(winning, bonusNumber, l));
        }
        //then
        assertThat(results.contains(LottoResult.FIRST)).isTrue();
        assertThat(results.contains(LottoResult.SECOND)).isTrue();
        assertThat(results.contains(LottoResult.THIRD)).isTrue();
        assertThat(results.contains(LottoResult.FOURTH)).isTrue();
        assertThat(results.contains(LottoResult.FIFTH)).isTrue();
    }

    @Test
    public void 전체_결과_확인() {
        //given
        Lotto winning = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 16));
        int bonusNumber = 17;
        int totalPrice = 6000;
        Lotto first = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 16));
        Lotto second = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 17));
        Lotto third = new Lotto(Arrays.asList(11, 12, 13, 14, 15, 18));
        Lotto fourth = new Lotto(Arrays.asList(11, 12, 13, 14, 18, 19));
        Lotto fifth = new Lotto(Arrays.asList(11, 12, 13, 18, 19, 20));
        Lotto none = new Lotto(Arrays.asList(11, 12, 18, 19, 20, 21));
        List<Lotto> lottos = Arrays.asList(first, second, third, fourth, fifth, none);
        //when
        ResultDto resultDto = lottoController.calculateTotalResult(winning, bonusNumber, totalPrice, lottos);
        //then
        assertThat(resultDto.resultMap().containsKey(LottoResult.FIFTH)).isTrue();
        assertThat(resultDto.resultMap().containsKey(LottoResult.SECOND)).isTrue();
        assertThat(resultDto.resultMap().containsKey(LottoResult.THIRD)).isTrue();
        assertThat(resultDto.resultMap().containsKey(LottoResult.FOURTH)).isTrue();
        assertThat(resultDto.resultMap().containsKey(LottoResult.FIFTH)).isTrue();

        long totalPrize = LottoResult.FIRST.getPrize() +
                LottoResult.SECOND.getPrize() +
                LottoResult.THIRD.getPrize() +
                LottoResult.FOURTH.getPrize() +
                LottoResult.FIFTH.getPrize();

        double roundedYield = lottoController.getRoundedYield(totalPrice, totalPrize);
        assertThat(resultDto.yield()).isEqualTo(roundedYield);
    }
}