package core

import chisel3._

class Adder extends Module {
  val io = IO(new Bundle {
    val a        = Input(UInt(8.W))
    val b        = Input(UInt(8.W))
    val out      = Output(UInt(8.W))
  })

  io.out := io.a + io.b
}
