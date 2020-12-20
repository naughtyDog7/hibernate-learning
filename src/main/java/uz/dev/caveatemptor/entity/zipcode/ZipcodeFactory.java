package uz.dev.caveatemptor.entity.zipcode;

class ZipcodeFactory {
    public static Zipcode fromString(String zipcodeStr) {
        if (zipcodeStr.length() == 5)
            return new GermanZipcode(zipcodeStr);
        else if (zipcodeStr.length() == 4)
            return new SwissZipcode(zipcodeStr);
        throw new IllegalArgumentException(
                "Unsupported zipcode in database: " + zipcodeStr
        );
    }
}
