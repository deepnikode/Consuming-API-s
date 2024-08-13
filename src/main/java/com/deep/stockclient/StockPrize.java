package com.deep.stockclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPrize {
    private  String symbol;
    private  Double prize;
    private LocalDateTime time;


}
