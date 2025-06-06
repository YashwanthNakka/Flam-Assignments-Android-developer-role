public class Main {
    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        map.put(1, 10);
        map.put(2, 20);
        System.out.println(map.get(1));  // 10
        System.out.println(map.get(3));  // -1
        map.put(2, 30);
        System.out.println(map.get(2));  // 30
        map.remove(2);
        System.out.println(map.get(2));  // -1
    }
}