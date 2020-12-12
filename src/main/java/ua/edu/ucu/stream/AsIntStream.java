package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Iterator;

public class AsIntStream implements IntStream, Iterable<Integer> {

    private Iterator<Integer> dataIterator;

    public AsIntStream(int... values) {
        ArrayList<Integer> data = new ArrayList<>();
        for (Integer i : values) {
            data.add(i);
        }
        this.dataIterator = data.iterator();
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public Double average() throws ArithmeticException {
        try {
            long sz = 0;
            int sm = 0;
            for (Integer i : this) {
                sz += 1;
                sm += i;
            }
            return (double) (sm / sz);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public Integer max() {
        if (!dataIterator.hasNext()) {
            throw new IllegalArgumentException();
        }
        return reduce(dataIterator.next(), Math::max);
    }

    @Override
    public Integer min() {
        if (!dataIterator.hasNext()) {
            throw new IllegalArgumentException();
        }
        return reduce(dataIterator.next(), Math::min);

    }

    @Override
    public long count() {
        long dataSize = 0;
        for (Integer i : this) {
            dataSize += 1;
        }
        return dataSize;
    }

    @Override
    public Integer sum() {
        if (!dataIterator.hasNext()) {
            throw new IllegalArgumentException();
        }
        return reduce(0, (sum, x) -> sum += x);

    }


    @Override
    public IntStream filter(IntPredicate predicate) {
        this.dataIterator = new Iterator<Integer>() {
            final Iterator<Integer> filterIterator = dataIterator;

            @Override
            public Integer next() {
                Integer curr_pos = filterIterator.next();
                while (!predicate.test(curr_pos)) {
                    curr_pos = filterIterator.next();
                }
                return curr_pos;
            }

            @Override
            public boolean hasNext() {
                return filterIterator.hasNext();
            }
        };
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        while (dataIterator.hasNext()) {
            action.accept(dataIterator.next());
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        this.dataIterator = new Iterator<Integer>() {
            final Iterator<Integer> mapIterator = dataIterator;

            @Override
            public boolean hasNext() {
                return mapIterator.hasNext();
            }

            @Override
            public Integer next() {
                return mapper.apply(mapIterator.next());
            }
        };
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        this.dataIterator = new Iterator<Integer>() {
            final Iterator<Integer> flatMapIterator = dataIterator;
            AsIntStream currStream = (AsIntStream) func.applyAsIntStream(flatMapIterator.next());

            @Override
            public boolean hasNext() {
                return currStream.dataIterator.hasNext() || flatMapIterator.hasNext();
            }

            @Override
            public Integer next() {
                if (!currStream.dataIterator.hasNext() && flatMapIterator.hasNext()) {
                    currStream = (AsIntStream) func.applyAsIntStream(flatMapIterator.next());
                }
                return currStream.dataIterator.next();
            }
        };
        return this;
    }


    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        for (Integer i : this) {
            identity = op.apply(identity, i);
        }
        return identity;
    }


    public int[] toArray() {
        ArrayList<Integer> rezArr = new ArrayList<>();
        for (Integer i : this) {
            rezArr.add(i);
        }
        int[] rez = new int[rezArr.size()];
        for (int i = 0; i < rezArr.size(); i++) {
            rez[i] = rezArr.get(i);
        }
        return rez;
    }

    @Override
    public Iterator<Integer> iterator() {
        return dataIterator;
    }

}
