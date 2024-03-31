package core

import chisel3._
import chisel3.iotesters._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TopTest extends AnyFlatSpec with ChiselScalatestTester {
      it must "runs Top" in { test(new Alu).withAnnotations(Seq(WriteFstAnnotation)) { dut =>
        // 通常のテスト
        dut.io.a.poke(3.U)
        dut.io.b.poke(5.U)
        dut.io.command.poke(1.U)
        // dut.io.command.poke(2.U)
        dut.clock.step(1)
        dut.io.out.expect(8.U)
        // オーバーフローしたときのテスト（今回はエラーになる）
        // dut.io.a.poke(2147483647.U)
        // dut.io.b.poke(10.U)
        // dut.io.command.poke(1.U)
        // dut.clock.step(1)
        // dut.io.out.expect(9.U)
      } }
}