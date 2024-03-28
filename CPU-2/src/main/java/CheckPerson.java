public class CheckPerson {
    public static void main(String[] args) {
        // オブジェクトのインスタンス化
        Person person1 = new Person("John", 30);
        Person person2 = new Person("Alice", 25);

        // メソッドの呼び出し
        person1.introduce();
        person2.introduce();

        // クラスメソッド（静的メソッド）の呼び出し
        Person.showPopulationCount();
    }
}
