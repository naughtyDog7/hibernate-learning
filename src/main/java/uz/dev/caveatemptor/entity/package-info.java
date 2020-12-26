@GenericGenerator(name = Constants.ID_GENERATOR,
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(name = "sequence_name", value = "IDS_SEQUENCE"),
                @Parameter(name = "initial_value", value = "1000"),
                @Parameter(name = "increment_size", value = "2")
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
package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmountUserType;
import uz.dev.caveatemptor.util.Constants;
