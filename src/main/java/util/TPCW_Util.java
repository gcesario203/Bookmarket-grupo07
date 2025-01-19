package util;


import java.util.*;
import java.util.stream.Collectors;

import dominio.Book;
import dominio.Customer;
import dominio.Review;

/**
 * *<img src="./doc-files/TPCW_Util.png" alt="TPCW_Util">
 */
public class TPCW_Util {

    private static final String[] arrayFnames = new String[]{
        "Alice", "Miguel", "Sophia", "Arthur", "Helena", "Bernardo", "Valentina", 
        "Heitor", "Laura", "Davi", "Isabella", "Lorenzo", "Manuela", "Théo", 
        "Júlia", "Pedro", "Heloísa", "Gabriel", "Luiza", "Enzo", "Maria", 
        "Luiza", "Matheus", "Lorena", "Lucas", "Lívia", "Benjamin", "Giovanna", 
        "Nicolas", "Marial", "Eduarda", "Guilherme", "Beatriz", "Rafael", "Mariali", 
        "Clara", "Joaquim", "Cecília", "Samuel", "Eloá", "Enzo", "Gabriel", "Lara", 
        "João", "Miguelito", "Maria", "Júlia", "Henrique", "Isadora", "Gustavo", 
        "Mariana", "Murilo", "Emanuelly", "Pedrosa", "Henrique", "Anana", "Júlia", 
        "Pietro", "Anani", "Luiza", "Lucca", "Anane", "Clara", "Felipe", "Melissa", 
        "João", "Pedrono", "Yasmin", "Isaac", "Mariari", "Alicy", "Benício", "Isabelly", 
        "Daniel", "Lavínia", "Anthony", "Esther", "Leonardo", "Sarah", "Davi", 
        "Lucca", "Sandro", "Elisa", "Bryan", "Antonella", "Eduardo", "Rafaela", 
        "João", "Lucas", "Marianna", "Cecília", "Victor", "Liz", "João", "Marina", 
        "Cauã", "Nicole", "Antônio", "Maitê", "Vicente", "Isis", "Caleb", "Alícia", 
        "Gael", "Luna", "Bento", "Rebeca", "Caio", "Agatha", "Emanuel", "Letícia", 
        "Vinícius", "Maria-Flor", "João", "Guilherme", "Gabriela", "Davi", "Lucas", 
        "Anna", "Laura", "Noah", "Catarina", "João", "Gabriel", "Clara", "João", 
        "Victor", "Ania", "Beatriz", "Luiz", "Miguela", "Vitória", "Francisco", 
        "Olívia", "Kaique", "Marianne", "Fernanda", "Otávio", "Emilly", "Augusto", 
        "Mariano", "Valentina", "Levi", "Milena", "Yuri", "Marianni", "Helena", "Enrico", 
        "Bianca", "Thiago", "Larissa", "Ian", "Mirella", "Victor", "Hugo", "Marianno", 
        "Flor", "Thomas", "Allana", "Henry", "Ania", "Sofia", "Luiz", "Felipe", "Clarice", 
        "Ryan", "Pietra", "Artur", "Miguelly", "Mariaty", "Vitória", "Davi", "Luiz", 
        "Maya", "Nathan", "Laís", "Pedrone", "Lucas", "Ayla", "Davi", "Miguelle", "Anne", 
        "Lívia", "Raul", "Eduarda", "Pedroso", "Miguelli", "Mariah", "Luiz", "Henrique", 
        "Stella", "Luan", "Anai", "Erick", "Gabrielly", "Martin", "Sophie", "Bruno", 
        "Carolina", "Rodrigo", "Mariallia", "Laura", "Luiz", "Gustavo", "Mariati", "Heloísa", 
        "Art", "Gabriel", "Mariami", "Soffia", "Breno", "Fernanda", "Kauê", "Malu", "Enzo", 
        "Miguelo", "Analu", "Fernando", "Amanda", "Arthuro", "Henrique", "Aurora", "Luiz", 
        "Otávio", "Mariassol", "Isis", "Carlos", "Eduardo", "Louise", "Tomás", "Heloise", 
        "Lucas", "Gabriel", "Ana", "Vitória", "André", "Ana", "Cecília", "José", "Anae", 
        "Liz", "Yago", "Joana", "Danilo", "Luana", "Anthony", "Gabriel", "Antônia", 
        "Ruan", "Isabel", "Miguelitto", "Henrique", "Bruna", "Oliver"
    };
    private static final String[] arrayLnames = new String[]{
        "Ferreira",
        "Braga",
        "da Silva",
        "Della Coletta",
        "Zampirolli",
        "Fernandes",
        "Alves",
        "Costalonga",
        "Botteon",
        "Caliman",
        "de Oliveira",
        "Zanette",
        "Salvador",
        "Silva",
        "Zandonadi",
        "Pesca",
        "Falqueto",
        "Tosi",
        "da Costa",
        "de Souza",
        "Gomes",
        "Calmon",
        "Pereira",
        "Sossai detto Pegorer",
        "de Almeida",
        "de Jesus",
        "Martins",
        "Balarini",
        "Rodrigues",
        "Gonçalves",
        "Pizzol",
        "Moreira",
        "Vieira",
        "Venturim",
        "Bazoni",
        "Filete",
        "Almeida",
        "Oliveira",
        "dos Santos",
        "Falchetto",
        "Barbosa",
        "Breda",
        "Scaramussa",
        "de Barros",
        "Marques"};

    /**
     *
     * @param rand
     * @return
     */
    public static String getRandomLname(Random rand) {
        final int index = getRandomInt(rand, 0, arrayLnames.length -1);
        return arrayLnames[index];
    }

    /**
     *
     * @param rand
     * @param min
     * @param max
     * @return
     */
    public static String getRandomString(Random rand, int min, int max) {
        StringBuilder newstring = new StringBuilder();
        final char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@', '#',
            '$', '%', '^', '&', '*', '(', ')', '_', '-', '=', '+',
            '{', '}', '[', ']', '|', ':', ';', ',', '.', '?', '/',
            '~', ' '}; //79 characters
        int strlen = getRandomInt(rand, min, max);
        for (int i = 0; i < strlen; i++) {
            newstring.append(chars[rand.nextInt(chars.length)]);
        }
        return newstring.toString();
    }

    /**
     *
     * @param rand
     * @param lower
     * @param upper
     * @return
     */
    public static int getRandomInt(Random rand, int lower, int upper) {
        return rand.nextInt(upper - lower + 1) + lower;
    }

    /**
     *
     * @param rand
     * @param lower
     * @param upper
     * @return
     */
    public static long getRandomLong(Random rand, long lower, long upper) {
        return (long) (rand.nextDouble() * (upper - lower + 1) + lower);
    }

    /**
     *
     * @param rand
     * @return
     */
    public static Date getRandomBirthdate(Random rand) {
        return new GregorianCalendar(
                TPCW_Util.getRandomInt(rand, 1880, 2000),
                TPCW_Util.getRandomInt(rand, 0, 11),
                TPCW_Util.getRandomInt(rand, 1, 30)).getTime();
    }

    /**
     *
     * @param rand
     * @return
     */
    public static Date getRandomPublishdate(Random rand) {
        return new GregorianCalendar(
                TPCW_Util.getRandomInt(rand, 1930, 2000),
                TPCW_Util.getRandomInt(rand, 0, 11),
                TPCW_Util.getRandomInt(rand, 1, 30)).getTime();
    }

    /**
     *
     * @param d
     * @param n
     * @return
     */
    public static String DigSyl(int d, int n) {
        StringBuilder resultString = new StringBuilder();
        String digits = Integer.toString(d);

        if (n > digits.length()) {
            int padding = n - digits.length();
            for (int i = 0; i < padding; i++) {
                resultString = resultString.append("BA");
            }
        }

        for (int i = 0; i < digits.length(); i++) {
            switch (digits.charAt(i)) {
                case '0':
                    resultString = resultString.append("BA");
                    break;
                case '1':
                    resultString = resultString.append("OG");
                    break;
                case '2':
                    resultString = resultString.append("AL");
                    break;
                case '3':
                    resultString = resultString.append("RI");
                    break;
                case '4':
                    resultString = resultString.append("RE");
                    break;
                case '5':
                    resultString = resultString.append("SE");
                    break;
                case '6':
                    resultString = resultString.append("AT");
                    break;
                case '7':
                    resultString = resultString.append("UL");
                    break;
                case '8':
                    resultString = resultString.append("IN");
                    break;
                case '9':
                    resultString = resultString.append("NG");
                    break;
                default:
                    break;
            }
        }

        return resultString.toString();
    }
    
    public static boolean areReviewFromAUniqueBookstore(List<Review> reviews, int bookstoreId) {
    	return reviews.stream().allMatch(r -> r.getBookstoreId() == bookstoreId);
    }
    
    public static boolean areAllReviewsFromTheSameBook(List<Review> reviews, Book book) {
    	return reviews.stream().allMatch(r -> r.getBook().getId() == book.getId());
    }
    
    public static boolean areAllReviewsFromTheSameCustomer(List<Review> reviews, Customer customer) {
    	return reviews.stream().allMatch(r -> r.getCustomer().getId() == customer.getId());
    }

}
