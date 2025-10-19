package org.example.stats.ActiveUsers;
import org.example.models.User.User;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ActiveUsersSpliterator implements Spliterator<User> {
    private final List<User> list;
    private int index;
    private final int end;

    public ActiveUsersSpliterator(List<User> list, int start, int end) {
        this.list = list;
        this.index = start;
        this.end = end;
    }

    @Override
    public boolean tryAdvance(Consumer<? super User> action) {
        if (index < end) {
            action.accept(list.get(index++));
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<User> trySplit() {
        int lo = index;
        int mid = (lo + end) >>> 1;
        if (lo >= mid) return null;
        index = mid;
        return new ActiveUsersSpliterator(list, lo, mid);
    }

    @Override
    public long estimateSize() {
        return end - index;
    }

    @Override
    public int characteristics() {
        return SIZED | SUBSIZED | IMMUTABLE;
    }
}