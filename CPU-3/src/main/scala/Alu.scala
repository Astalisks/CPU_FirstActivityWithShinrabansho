package core

import chisel3._
import chisel3.util._

class Alu extends Module {
  val io = IO(new Bundle {
    val command  = Input(UInt(8.W))  // ALUに対するコマンド (8ビット)
    val a        = Input(UInt(32.W)) // ALUへの入力データa (32ビット)
    val b        = Input(UInt(32.W)) // ALUへの入力データb (32ビット)
    val zero     = Output(Bool())    // 演算結果がゼロかを示す信号 (1ビット)
    val out      = Output(UInt(32.W))// 演算結果を出力 (32ビット)
  })
  
  // 演算結果がゼロの場合にzero信号をTrueに設定する
  io.zero := (io.out === 0.U(32.W)) // io.out がゼロなら、io.zero に True を出力

  // コマンドに応じてALUの演算を選択
  io.out := MuxCase(0.U(32.W), Seq(
    // コマンドが 1 の場合、a + b を実行
    (io.command === 1.U(8.W)) -> (io.a + io.b),
    
    // コマンドが 2 の場合、a - b を実行
    (io.command === 2.U(8.W)) -> (io.a - io.b)
  ))
}
