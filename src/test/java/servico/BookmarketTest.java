package servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import dominio.Book;
import dominio.Customer;
import dominio.Review;
import util.TPCW_Util;

public class BookmarketTest {

	static Bookstore amazon;
	static Bookstore saraiva;
	
	static Bookmarket bookmarket;
	public BookmarketTest() {
		
	}
    @BeforeClass
    public static void setUpClass() {
        long seed = 178;
        long now = System.currentTimeMillis();
        Random rand = new Random(seed);
        Bookstore.populate(seed, now, 10000, 1000, 100, 1000);
        
        amazon = new Bookstore(0);
        saraiva = new Bookstore(1);
        
        amazon.populateInstanceBookstore(10000, rand, now);
        
        saraiva.populateInstanceBookstore(1000, rand, now);
        
        bookmarket = new Bookmarket();
        
        bookmarket.init(amazon, saraiva);
    }
    
    @Test
    public void bookstoresShouldHaveDifferentListOfReviews() {
    	List<Review> reviewsFromAmazon = amazon.getReviews();
    	
    	List<Review> reviewsFromSaraiva = saraiva.getReviews();
    	
    	List<Review> allReviews = bookmarket.getReviews();
    	
    	assertEquals(reviewsFromAmazon.size() + reviewsFromSaraiva.size(), allReviews.size());
    	
    	assertFalse(reviewsFromAmazon.equals(reviewsFromSaraiva));
    }
    
    @Test
    public void shouldGetAReviewThatExistsInABookstore() {
    	List<Review> amazonReviews = bookmarket.getReviewsByBookstore(amazon.getId());
    	
    	List<Review> saraivaReviews = bookmarket.getReviewsByBookstore(saraiva.getId());
    	
    	assertTrue(TPCW_Util.areReviewFromAUniqueBookstore(saraivaReviews, saraiva.getId()));
    	
    	assertTrue(TPCW_Util.areReviewFromAUniqueBookstore(amazonReviews, amazon.getId()));
    }
    
    @Test
    public void shouldCreateAReviewForABookstore() {
    	Customer amazonCustomer = amazon.getCustomer(1);
    	
    	Book amazonBook = amazon.getABookAnyBook(new Random(0));
    	
    	Review amazonReview = bookmarket.createReview(amazon.getId(), amazonCustomer, amazonBook, Math.random() * 5);
    	
    	assertTrue(bookmarket.getReviewById(amazon.getId(), amazonReview.getId()).get().getId() == amazonReview.getId());
    	
    	assertFalse(bookmarket.getReviewById(saraiva.getId(), amazonReview.getId()).isPresent());
    }
    
    @Test
    public void shouldChangeAReviewFromABookstore() {
    	Review saraivaReview = saraiva.getReviews().get(0);
    	
    	double reviewLastValue = saraivaReview.getRating();
    	
    	double newValue = Math.random() * 6;
    	
    	while(reviewLastValue == newValue || newValue > 5)
    		newValue = Math.random() * 6;
    	
    	boolean result = bookmarket.changeReviewValue(saraiva.getId(), saraivaReview.getId(), newValue);
    	
    	Review changedSaraivaReview = bookmarket.getReviewById(saraiva.getId(), saraivaReview.getId()).get();
    	
    	assertTrue(result);
    	
    	assertEquals(changedSaraivaReview.getId(), saraivaReview.getId());
    	
    	assertTrue(changedSaraivaReview.getRating() == newValue);
    	
    	assertFalse(changedSaraivaReview.getRating() == reviewLastValue);
    }
    
    @Test
    public void shouldRemoveAReviewFromABookstore() {
    	List<Review> saraivaReviews = bookmarket.getReviewsByBookstore(saraiva.getId());
    	
    	Review saraivaReview = saraivaReviews.get(0);
    	
    	int saraivaLastSize = saraivaReviews.size();
    	
    	boolean result = bookmarket.removeReviewById(saraiva.getId(), saraivaReview.getId());
    	
    	assertTrue(result);
    	
    	assertTrue(saraivaLastSize == (bookmarket.getReviewsByBookstore(saraiva.getId()).size() + 1));
    	
    	assertFalse(bookmarket.removeReviewById(saraiva.getId(), saraivaReview.getId()));
    	
    	assertFalse(bookmarket.getReviewById(saraiva.getId(), saraivaReview.getId()).isPresent());
    }
    
    @Test
    public void shouldAllReviewTobeFromTheSameBook() {
    	Book amazonBook = amazon.getABookAnyBook(new Random(0));
    	
    	Book saraivaBook = saraiva.getABookAnyBook(new Random(2));
    	 
    	List<Review> amazonReviews = bookmarket.getReviewsByBook(amazon.getId(), amazonBook);
    	
    	List<Review> saraivaReviews = bookmarket.getReviewsByBook(saraiva.getId(), saraivaBook);
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(saraivaReviews, saraivaBook));
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(amazonReviews, amazonBook));
    }
    
    @Test
    public void shouldAllReviewTobeFromTheSameCustomer() {
    	Customer amazonCustomer = amazon.getCustomer(1);
    	
    	Customer saraivaCustomer = saraiva.getCustomer(2);
    	 
    	List<Review> amazonReviews = bookmarket.getReviewsByCustomer(amazon.getId(), amazonCustomer);
    	
    	List<Review> saraivaReviews = bookmarket.getReviewsByCustomer(saraiva.getId(), saraivaCustomer);
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(saraivaReviews, saraivaCustomer));
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(amazonReviews, amazonCustomer));
    }
}
