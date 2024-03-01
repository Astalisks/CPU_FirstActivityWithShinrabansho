package core

import chisel3._
import chiseltest._
import chisel3.iotesters._
import org.scalatest.flatspec.AnyFlatSpec

class TopTest extends AnyFlatSpec with ChiselScalatestTester {
      it must "runs Top" in { test(new Adder).withAnnotations(Seq(WriteFstAnnotation)) { c =>
        // 通常のテスト
        c.io.a.poke(3.U)
        c.io.b.poke(5.U)
        c.clock.step(1)
        c.io.out.expect(8.U)
        // オーバーフローしたときのテスト
        c.io.a.poke(250.U)
        c.io.b.poke(10.U)
        c.clock.step(1)
        c.io.out.expect(4.U)
      } }
}
