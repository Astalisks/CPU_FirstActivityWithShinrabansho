public class Person {
    // インスタンス変数をprivateに設定してカプセル化
    private String name;
    private int age;

    // クラス変数（静的変数）
    private static int populationCount = 0;

    // コンストラクタ
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        Person.populationCount++; // 人口カウントを増やす
    }

    // 名前のゲッター
    public String getName() {
        return name;
    }

    // 年齢のゲッター
    public int getAge() {
        return age;
    }

    // メソッド
    public void introduce() {
        System.out.println("Hello, my name is " + name + " and I am " + age + " years old.");
    }

    // クラスメソッド（静的メソッド）
    public static void showPopulationCount() {
        System.out.println("Population count is: " + populationCount);
    }
}

