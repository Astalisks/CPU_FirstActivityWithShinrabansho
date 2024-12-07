package core

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TopTest extends AnyFlatSpec with ChiselScalatestTester {
  "Core" should "execute instructions correctly" in {
    test(new Core).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      // クロックを3サイクル進める
      dut.clock.step(3)

      // 結果の検証
      dut.io.out.expect(8.U)
    }
  }
}

