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
  loadMemoryFromFile(mem, "src/main/resources/02_sub-subi.hex")

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
  val regfile = RegInit(VecInit(Seq.tabulate(32) { i => 0.U(32.W) }))

  // ALUのインスタンス化
  val alu = Module(new Alu)

  // ALUに発行するコマンド
  val command = Wire(UInt(8.W))
  command := MuxCase(0.U(8.W), Seq(
    (opcode === 1.U(5.W) && opcode_sub === 1.U(3.W)) -> (1.U(8.W)), // add
    (opcode === 1.U(5.W) && opcode_sub === 2.U(3.W)) -> (2.U(8.W)), // sub

    (opcode === 2.U(5.W) && opcode_sub === 1.U(3.W)) -> (1.U(8.W)), // addi
    (opcode === 2.U(5.W) && opcode_sub === 2.U(3.W)) -> (2.U(8.W)), // subi

    // opcode === 3.U ~ 6.U は次回以降使用する

    (opcode === 7.U(5.W) && opcode_sub === 0.U(3.W)) -> (3.U(8.W)), // and
    (opcode === 7.U(5.W) && opcode_sub === 1.U(3.W)) -> (4.U(8.W)), // or
    (opcode === 7.U(5.W) && opcode_sub === 2.U(3.W)) -> (5.U(8.W)), // xor
    (opcode === 7.U(5.W) && opcode_sub === 3.U(3.W)) -> (6.U(8.W)), // srl
    (opcode === 7.U(5.W) && opcode_sub === 4.U(3.W)) -> (7.U(8.W)), // sra
    (opcode === 7.U(5.W) && opcode_sub === 5.U(3.W)) -> (8.U(8.W)), // sll

    (opcode === 8.U(5.W) && opcode_sub === 0.U(3.W)) -> (3.U(8.W)), // andi
    (opcode === 8.U(5.W) && opcode_sub === 1.U(3.W)) -> (4.U(8.W)), // ori
    (opcode === 8.U(5.W) && opcode_sub === 2.U(3.W)) -> (5.U(8.W)), // xori
    (opcode === 8.U(5.W) && opcode_sub === 3.U(3.W)) -> (6.U(8.W)), // srli
    (opcode === 8.U(5.W) && opcode_sub === 4.U(3.W)) -> (7.U(8.W)), // srai
    (opcode === 8.U(5.W) && opcode_sub === 5.U(3.W)) -> (8.U(8.W)), // slli
  ))

  // Execute部（計算実行）
  alu.io.command := command
  alu.io.a       := MuxCase(regfile(rs1), Seq(
    (opcode === 2.U(5.W)) -> (regfile(rs1)),                            // addi, subi
    // opcode === 3.U ~ 6.U は次回以降使用する
    // opcode === 7.U(5.W)はrs1を使用しない
    (opcode === 8.U(5.W)) -> (regfile(rs1)),                            // andi, ori, xori, srli, srai, slli
  ))
  alu.io.b       := MuxCase(0.U(32.W), Seq(
    (opcode === 1.U(5.W)) -> (regfile(rs2)),                              // add, sub
    (opcode === 2.U(5.W)) -> (imm),                                       // addi, subi
    // opcode === 3.U ~ 6.U は次回以降使用する
    (opcode === 7.U(5.W)) -> (regfile(rs2)),                              // and, or, xor, srl, sra, sll
    (opcode === 8.U(5.W)) -> (imm),                                       // andi, ori, xori, srli, srai, slli
  ))

  // 書き戻し
  regfile(rd) := alu.io.out

  // デバッグ用
  printf(p"------pc    : 0x${Hexadecimal(pc)} ------\n")
  printf(p"instr       : 0x${Hexadecimal(instr)}\n")
  printf(p"opcode      : 0x${Hexadecimal(opcode)}\n")
  printf(p"opcode_sub  : 0x${Hexadecimal(opcode_sub)}\n")
  printf(p"rs1         : 0x${Hexadecimal(rs1)}\n")
  printf(p"regfile(rs1): 0x${Hexadecimal(regfile(rs1))}\n")
  printf(p"regfile(rs2): 0x${Hexadecimal(regfile(rs2))}\n")
  printf(p"imm         : 0x${Hexadecimal(imm)}\n")
  printf(p"command     : 0x${Hexadecimal(command)}\n")
  printf(p"alu.io.a    : 0x${Hexadecimal(alu.io.a)}\n")
  printf(p"alu.io.b    : 0x${Hexadecimal(alu.io.b)}\n")
  printf(p"alu.io.out  : 0x${Hexadecimal(alu.io.out)}\n")
  printf(p"-------------------------------\n\n")

  // テスト用の出力
  io.out := regfile(3)

  // プログラムカウンタの更新
  pc := pc + 6.U
}

