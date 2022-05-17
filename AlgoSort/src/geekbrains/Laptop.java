package geekbrains;

public record Laptop(int price, int ram, Brand brand) implements Comparable<Laptop> {

    @Override
    public int compareTo(Laptop o) {
        return price != o.price ? price - o.price :
               ram != o.ram     ? ram - o.ram :
               brand.compareTo(o.brand);
    }
}
