package servico.bookmarket.statemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.exceptions.UmbrellaException;
import servico.bookmarket.interfaces.Action;

public class StateMachine {

    private final List<Bookstore> state;

    public StateMachine(final List object) {
        this.state = object;
    }

    public Object execute(Action action) {
        return action.executeOn(getStateStream());
    }

    public Stream<Bookstore> getStateStream() {
        return state.stream();
    }

    public static StateMachine create(Bookstore... state) {
        List list = new ArrayList();
        try {
            list.addAll(Arrays.asList(state));
        } catch (Exception e) {
            throw new UmbrellaException(e);
        }
        return new StateMachine(list);
    }

}
