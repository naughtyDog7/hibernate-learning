@GenericGenerator(name = Constants.ID_GENERATOR,
        strategy = "enhanced-sequence",
        parameters = {
                @Parameter(name = "sequence_name", value = "IDS_SEQUENCE"),
                @Parameter(name = "initial_value", value = "1000"),
                @Parameter(name = "increment_size", value = "2")
        })
package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import uz.dev.caveatemptor.util.Constants;
