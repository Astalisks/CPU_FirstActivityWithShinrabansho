package core

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TopTest extends AnyFlatSpec with ChiselScalatestTester {
  "Core" should "execute instructions correctly" in {
    test(new Core).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      // クロックを進める
      dut.clock.step(4)

      // 結果の検証
      dut.io.out.expect(8900.U)
    }
  }
}

