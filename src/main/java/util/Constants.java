package util;

public class Constants {
    public static final String[] COUNTRIES = {
            "United States", "United Kingdom", "Canada",
            "Germany", "France", "Japan", "Netherlands",
            "Italy", "Switzerland", "Australia", "Algeria",
            "Argentina", "Armenia", "Austria", "Azerbaijan",
            "Bahamas", "Bahrain", "Bangla Desh", "Barbados",
            "Belarus", "Belgium", "Bermuda", "Bolivia",
            "Botswana", "Brazil", "Bulgaria", "Cayman Islands",
            "Chad", "Chile", "China", "Christmas Island",
            "Colombia", "Croatia", "Cuba", "Cyprus",
            "Czech Republic", "Denmark", "Dominican Republic",
            "Eastern Caribbean", "Ecuador", "Egypt",
            "El Salvador", "Estonia", "Ethiopia",
            "Falkland Island", "Faroe Island", "Fiji",
            "Finland", "Gabon", "Gibraltar", "Greece", "Guam",
            "Hong Kong", "Hungary", "Iceland", "India",
            "Indonesia", "Iran", "Iraq", "Ireland", "Israel",
            "Jamaica", "Jordan", "Kazakhstan", "Kuwait",
            "Lebanon", "Luxembourg", "Malaysia", "Mexico",
            "Mauritius", "New Zealand", "Norway", "Pakistan",
            "Philippines", "Poland", "Portugal", "Romania",
            "Russia", "Saudi Arabia", "Singapore", "Slovakia",
            "South Africa", "South Korea", "Spain", "Sudan",
            "Sweden", "Taiwan", "Thailand", "Trinidad",
            "Turkey", "Venezuela", "Zambia"
    };

    public static final double[] EXCHANGES = {
            1, .625461, 1.46712, 1.86125, 6.24238, 121.907,
            2.09715, 1842.64, 1.51645, 1.54208, 65.3851,
            0.998, 540.92, 13.0949, 3977, 1, .3757,
            48.65, 2, 248000, 38.3892, 1, 5.74, 4.7304,
            1.71, 1846, .8282, 627.1999, 494.2, 8.278,
            1.5391, 1677, 7.3044, 23, .543, 36.0127,
            7.0707, 15.8, 2.7, 9600, 3.33771, 8.7,
            14.9912, 7.7, .6255, 7.124, 1.9724, 5.65822,
            627.1999, .6255, 309.214, 1, 7.75473, 237.23,
            74.147, 42.75, 8100, 3000, .3083, .749481,
            4.12, 37.4, 0.708, 150, .3062, 1502, 38.3892,
            3.8, 9.6287, 25.245, 1.87539, 7.83101,
            52, 37.8501, 3.9525, 190.788, 15180.2,
            24.43, 3.7501, 1.72929, 43.9642, 6.25845,
            1190.15, 158.34, 5.282, 8.54477, 32.77, 37.1414,
            6.1764, 401500, 596, 2447.7
    };

    public static final String[] CURRENCIES = {
            "Dollars", "Pounds", "Dollars", "Deutsche Marks",
            "Francs", "Yen", "Guilders", "Lira", "Francs",
            "Dollars", "Dinars", "Pesos", "Dram",
            "Schillings", "Manat", "Dollars", "Dinar", "Taka",
            "Dollars", "Rouble", "Francs", "Dollars",
            "Boliviano", "Pula", "Real", "Lev", "Dollars",
            "Franc", "Pesos", "Yuan Renmimbi", "Dollars",
            "Pesos", "Kuna", "Pesos", "Pounds", "Koruna",
            "Kroner", "Pesos", "Dollars", "Sucre", "Pounds",
            "Colon", "Kroon", "Birr", "Pound", "Krone",
            "Dollars", "Markka", "Franc", "Pound", "Drachmas",
            "Dollars", "Dollars", "Forint", "Krona", "Rupees",
            "Rupiah", "Rial", "Dinar", "Punt", "Shekels",
            "Dollars", "Dinar", "Tenge", "Dinar", "Pounds",
            "Francs", "Ringgit", "Pesos", "Rupees", "Dollars",
            "Kroner", "Rupees", "Pesos", "Zloty", "Escudo",
            "Leu", "Rubles", "Riyal", "Dollars", "Koruna",
            "Rand", "Won", "Pesetas", "Dinar", "Krona",
            "Dollars", "Baht", "Dollars", "Lira", "Bolivar",
            "Kwacha"
    };

    public enum Subject {
        ARTS, BIOGRAPHIES, BUSINESS, CHILDREN, COMPUTERS, COOKING, HEALTH, HISTORY, HOME, HUMOR,
        LITERATURE, MYSTERY, NON_FICTION, PARENTING, POLITICS, REFERENCE, RELIGION, ROMANCE,
        SELF_HELP, SCIENCE_NATURE, SCIENCE_FICTION, SPORTS, YOUTH, TRAVEL
    }

    public enum Backing {
        HARDBACK, PAPERBACK, USED, AUDIO, LIMITED_EDITION
    }

    public static final String[] CREDIT_CARDS = {"VISA", "MASTERCARD", "DISCOVER", "AMEX", "DINERS"};

    public static final String[] SHIP_TYPES = {"AIR", "UPS", "FEDEX", "SHIP", "COURIER", "MAIL"};

    public static final String[] STATUS_TYPES = {"PROCESSING", "SHIPPED", "PENDING", "DENIED"};
}
