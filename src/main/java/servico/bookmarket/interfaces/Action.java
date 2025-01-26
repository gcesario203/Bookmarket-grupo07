package servico.bookmarket.interfaces;

public interface Action<STATE> {

    Object executeOn(STATE sm);
}
