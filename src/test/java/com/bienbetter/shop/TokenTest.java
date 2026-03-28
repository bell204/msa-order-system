package com.bienbetter.shop;

import com.bienbetter.shop.common.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TokenTest {

    private static final String VALID_TOKEN = "RmowS21GTm9FaEs3OHZHSy81OWRwZTZIMVkxMjFibjdLaGhMYUs3bGRGaz0=.MjU2OjE3Njc2NjE0NzM2NTU=";

    @Test
    void createToken_then_parseToken_should_return_same_boardId() throws Exception {
        Long expectedBoardId  = 256L;
        Long parsedBoardId = new TokenUtil().parseToken(VALID_TOKEN);
        boolean result = expectedBoardId.equals(parsedBoardId);

        log.info("Expected boardId : {}", expectedBoardId);
        log.info("Parsed boardId   : {}", parsedBoardId);
        log.info("Validation result: {}", result);
        assertTrue(result);
    }
}