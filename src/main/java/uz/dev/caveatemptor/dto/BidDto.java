package uz.dev.caveatemptor.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.dev.caveatemptor.entity.Bid;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@ToString
public class BidDto {
    private long id;
    private BigDecimal amountValue;
    private Currency amountCurrency;
    private long bidderId;
    private long itemId;

    private BidDto(long id, BigDecimal amountValue, Currency amountCurrency, long bidderId, long itemId) {
        this.id = id;
        this.amountValue = amountValue;
        this.amountCurrency = amountCurrency;
        this.bidderId = bidderId;
        this.itemId = itemId;
    }

    public static BidDto fromBid(Bid bid) {
        return new BidDto(bid.getId(), bid.getAmount().getValue(), bid.getAmount().getCurrency(),
                bid.getBidder().getId(), bid.getItem().getId());
    }
}
