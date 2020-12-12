package ua.edu.ucu.stream;

import ua.edu.ucu.function.IntBinaryOperator;
import ua.edu.ucu.function.IntConsumer;
import ua.edu.ucu.function.IntPredicate;
import ua.edu.ucu.function.IntToIntStreamFunction;
import ua.edu.ucu.function.IntUnaryOperator;
import java.util.ArrayList;


public class AsIntStream implements IntStream {

    private ArrayList<Integer> list;

    private AsIntStream() {
    }

    private AsIntStream(int... values) {
        this.list = new ArrayList<>();
        for (int value: values) {
            this.list.add(value);
        }

    }

    public AsIntStream(ArrayList<Integer> newList) {
        this.list = new ArrayList<>();
        for (int values: newList) {
            this.list.add(values);
        }
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public Double average() {
        double sum = 0;
        int size = 0;
        for (int el : list) {
            size += 1;
            sum += el;
        }
        return sum / size;
    }

    @Override
    public Integer max() {
        return this.reduce(Integer.MIN_VALUE, Math::max);
    }

    @Override
    public Integer min() {
        return this.reduce(Integer.MAX_VALUE, Math::min);
    }

    @Override
    public long count() {
        return list.size();
    }

    @Override
    public Integer sum() {
        return this.reduce(0, Integer::sum);
    }



    @Override
    public IntStream filter(IntPredicate predicate) {
        ArrayList<Integer> newList = new ArrayList<Integer>();
        for (int el : list) {
            if (predicate.test(el)) {
                newList.add(el);
            }
        }
        return new AsIntStream(newList);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int value : list) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        ArrayList<Integer> newList = new ArrayList<Integer>();
        for (int el : list) {
            newList.add(mapper.apply(el));
            }
        return new AsIntStream(newList);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList<Integer> newList = new ArrayList<Integer>();
        for (int el : list) {
            newList.addAll(((AsIntStream) func.applyAsIntStream(el)).list);

        }
        return new AsIntStream(newList);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int id = identity;
        for (int el: list) {
            id = op.apply(id, el);
        }
        return id;
    }

    @Override
    public int[] toArray() {
        int[] array = new int[list.size()];
        for (int i =0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

}






