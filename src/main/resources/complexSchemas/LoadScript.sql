ALTER TABLE BID
    ADD CONSTRAINT AUCTION_BID_TIME
        check (
                CREATEDON <= (
                select i.AUCTIONEND
                FROM ITEM i
                WHERE i.ID = ITEM_ID
                )
            );