@GenericGenerator(name = Constants.ID_GENERATOR,
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(name = "sequence_name", value = "IDS_SEQUENCE"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "100"),
                @Parameter(name = "optimizer", value = "pooled-lo")
        })
@TypeDefs({
        @TypeDef(
                name = "monetary_amount",
                typeClass = MonetaryAmountUserType.class),
        @TypeDef(
                name = "monetary_amount_usd",
                typeClass = MonetaryAmountUserType.class,
                parameters = @Parameter(name = "convertTo", value = "USD")),
        @TypeDef(
                name = "monetary_amount_eur",
                typeClass = MonetaryAmountUserType.class,
                parameters = @Parameter(name = "convertTo", value = "EUR")),
})
@FilterDef(
        name = Constants.Filters.LIMIT_BY_USER_RANK,
        parameters = @ParamDef(
                name = Constants.QueryParams.CURRENT_USER_RANK, type = "int"
        )
)
@NamedQueries({
        @NamedQuery(
                name = Constants.QueryNames.SELECT_ITEM_BY_LIKE_NAME,
                query = "SELECT i FROM Item i WHERE i.name LIKE :name")
})
package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.*;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmountUserType;
import uz.dev.caveatemptor.util.Constants;
