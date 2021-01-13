ALTER TABLE BID
    ADD CONSTRAINT AUCTION_BID_TIME
        check (
                CREATEDON <= (
                SELECT i.AUCTIONEND
                FROM ITEM i
                WHERE i.ID = ITEM_ID
                )
            );
ALTER TABLE BID
    ADD CONSTRAINT SELLER_AS_BIDDER
        check (
                BIDDER_ID != (
                    SELECT i.SELLER_ID FROM ITEM i
                    WHERE i.ID = ITEM_ID
                    )
                );