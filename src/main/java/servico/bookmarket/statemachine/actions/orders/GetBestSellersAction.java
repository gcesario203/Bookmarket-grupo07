package servico.bookmarket.statemachine.actions.orders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dominio.Book;
import servico.bookmarket.statemachine.actions.BookstoreAction;
import servico.bookstore.Bookstore;

/**
 * Ação da maquina de estado da Bookmarket responsável por retornar uma lista dos livros
 * mais vendidos, podendo ser filtrada por subject
 */
public class GetBestSellersAction extends BookstoreAction {

	private static final long serialVersionUID = 34893412122L;
	
	String subject;
	Integer numberOfBooks;
	
	public GetBestSellersAction(String subject, Integer numberOfBooks) {
		this.subject = subject;
		this.numberOfBooks = numberOfBooks;
	}
	
	/*
	 * Busca os best-sellers de todas as bookstore, unificamos num mesmo hashmap, ordenamos
	 * pelos mais vendidos e retornamos limitando pela quantidade passada como parametro
	 */
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return bookstore
						.map(existingBookstore -> existingBookstore.getBestSellers(subject, numberOfBooks))
						.flatMap(map -> map.entrySet().stream())
			            .collect(Collectors.toMap(
			                    Map.Entry::getKey,      // Mantém a chave (Book)
			                    Map.Entry::getValue,    // Mantém o valor (Integer)
			                    Integer::sum,           // Em caso de chave duplicada, soma os valores
			                    HashMap::new            // Usa um HashMap para armazenar o resultado
			                ))
			            .entrySet().stream() // Converte para Stream para ordenar
			            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Ordena por quantidade (descendente)
			            .collect(Collectors.toMap(
			                Map.Entry::getKey,
			                Map.Entry::getValue,
			                (oldValue, newValue) -> oldValue, // Resolver conflitos (não ocorrem aqui)
			                LinkedHashMap::new // Mantém a ordem ordenada
			            ))
			            .entrySet().stream()
			            .map(Map.Entry::getKey) // Extrai apenas os livros (Book)
			            .limit(numberOfBooks)
			            .collect(Collectors.toList()); // Coleta em uma lista
	}

}
