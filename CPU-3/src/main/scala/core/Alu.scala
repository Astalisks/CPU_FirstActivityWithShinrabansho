package core

import chisel3._
import chisel3.util._

class Alu extends Module {
  val io = IO(new Bundle {
    val command  = Input(UInt(8.W))
    val a        = Input(UInt(32.W))
    val b        = Input(UInt(32.W))
    val zero     = Output(Bool())
    val out      = Output(UInt(32.W))
  })

  io.zero := (io.out === 0.U(32.W))  // 出力が0のときに1になる信号

  io.out := MuxCase(0.U(32.W), Seq(
    (io.command === 1.U(8.W)) -> (io.a + io.b),
    (io.command === 2.U(8.W)) -> (io.a - io.b),
    (io.command === 3.U(8.W)) -> (io.a & io.b),                      // and
    (io.command === 4.U(8.W)) -> (io.a | io.b),                      // or
    (io.command === 5.U(8.W)) -> (io.a ^ io.b),                      // xor
    (io.command === 6.U(8.W)) -> (io.a >> io.b(4, 0)),               // 右論理シフト
    (io.command === 7.U(8.W)) -> (io.a.asSInt >> io.b(4, 0)).asUInt, // 右算術シフト
    (io.command === 8.U(8.W)) -> (io.a << io.b(4, 0)),               // 左論理シフト
  ))
}
