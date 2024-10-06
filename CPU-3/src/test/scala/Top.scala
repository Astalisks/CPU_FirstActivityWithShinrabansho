package core

import chisel3._
import chisel3.iotesters._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TopTest extends AnyFlatSpec with ChiselScalatestTester {
  it must "runs Top" in { 
    test(new Alu).withAnnotations(Seq(WriteFstAnnotation)) { dut =>
      // add命令のテスト (3 + 5 = 8)
      dut.io.a.poke(3.U)       // 入力 a に 3 をセット
      dut.io.b.poke(5.U)       // 入力 b に 5 をセット
      dut.io.command.poke(1.U) // コマンド 1 (add) をセット
      dut.clock.step(1)        // クロックを1ステップ進める
      dut.io.out.expect(8.U)   // 結果が 8 であることを確認

    } 
  }
}
