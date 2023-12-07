import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite {
  test("Main should print 'Hello, World!'") {
    // 予測される出力をキャプチャするための準備
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream) {
      // Mainを実行
      Main.main(Array.empty)
    }

    // 出力のアサーション
    assert(stream.toString.trim == "Hello, World!")
  }
}
