package servico.bookmarket.statemachine.actions.reviews;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;
import dominio.Review;

/**
 * Ação responsável por obter reviews únicos, considerando a média das avaliações
 * em caso de duplicação (mesmo cliente e mesmo livro).
 * <p>
 * Esse método agrupa as reviews pelo par (cliente, livro) e, para cada grupo,
 * calcula a média das avaliações. Em seguida, cria uma nova instância de {@code Review}
 * com o mesmo cliente e livro, e com a avaliação sendo a média calculada.
 * Caso ocorra um {@code IOException} durante a criação da review, uma {@code RuntimeException}
 * será lançada.
 * </p>
 */
public class GetUniqueReviewsAction extends BookstoreAction {
    private static final long serialVersionUID = 7439962163328790678L;

    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        // Agrupa as reviews por chave composta pelo ID do cliente e do livro
        Map<String, List<Review>> groupedReviews = bookstore
                .flatMap(b -> b.getReviews().stream())
                .collect(Collectors.groupingBy(r -> r.getCustomer().getId() + "-" + r.getBook().getId()));

        // Para cada grupo, calcula a média das avaliações e cria uma review única
        return groupedReviews.values().stream()
                .map(reviewsGroup -> {
                    Review baseReview = reviewsGroup.get(0);
                    double averageRating = reviewsGroup.stream()
                            .mapToDouble(Review::getRating)
                            .average()
                            .orElse(baseReview.getRating());
                    try {
                        return new Review(baseReview.getCustomer(), baseReview.getBook(), averageRating, 0);
                    } catch (IOException e) {
                        throw new RuntimeException("Erro ao criar a review com média", e);
                    }
                })
                .collect(Collectors.toList());
    }
}
