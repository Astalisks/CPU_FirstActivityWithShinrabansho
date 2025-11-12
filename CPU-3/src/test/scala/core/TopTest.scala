package core

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TopTest extends AnyFlatSpec with ChiselScalatestTester {
  "Core" should "execute instructions correctly" in {
    test(new Core).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      // クロックをn命令ぶんだけ進める
      dut.clock.step(7)

      // 結果の検証
      dut.io.out.expect(1.U)
    }
  }
}
