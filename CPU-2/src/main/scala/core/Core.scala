package core

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class Core extends Module {

  val io = IO(new Bundle {
    val out = Output(UInt(32.W))
  })

  // メモリの定義と初期化
  val mem = Mem(1024 * 6, UInt(8.W))
  loadMemoryFromFile(mem, "src/main/resources/bootrom.hex")

  val pc = RegInit(0.U(32.W))

  val instr      = Wire(UInt(48.W))
  val opcode     = Wire(UInt(5.W))
  val opcode_sub = Wire(UInt(3.W))
  val rd         = Wire(UInt(5.W))
  val rs1        = Wire(UInt(5.W))
  val rs2        = Wire(UInt(5.W))
  val imm        = Wire(UInt(32.W))

  // Fetch
  instr := Cat(
    (0 until 6).map(i => mem.read(pc + i.U)).reverse
  )

  // Decode
  opcode     := instr( 4,  0)
  opcode_sub := instr( 7,  5)
  rd         := instr(12,  8)
  rs1        := instr(17, 13)
  rs2        := instr(22, 18)
  imm        := instr(47, 16)

  // レジスタファイルの定義と初期化
  val regs = RegInit(VecInit(Seq.tabulate(32) { i => 0.U(32.W) }))

  // ALUのインスタンス化
  val alu = Module(new Alu)
  alu.io.command := opcode
  alu.io.a := regs(rs1)
  // alu.io.b := regs(rs2)
  alu.io.b := MuxCase(0.U(32.W), Seq(
    (opcode === 1.U(5.W)) -> regs(rs2),
    (opcode === 2.U(5.W)) -> imm,
  ))

  // 書き戻し
  when(opcode === 1.U(5.W)) {
    regs(rd) := alu.io.out
  }
  when(opcode === 2.U(5.W)) {
    regs(rd) := alu.io.out
  }

  // テスト用の出力
  io.out := regs(2)

  // プログラムカウンタの更新
  pc := pc + 6.U
}
