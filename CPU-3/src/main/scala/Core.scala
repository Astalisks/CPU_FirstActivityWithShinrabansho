package core

import chisel3._
import chisel3.util._

class Core extends Module {

  // 6KBのメモリを定義
  val mem = Mem(1024 * 6, UInt(8.W))
  
  // プログラムカウンタを初期化
  val pc = RegInit(0.U(32.W))
  
  // 32個の32ビット幅のレジスタファイルを定義
  val regfile = Mem(32, UInt(32.W))

  // 命令に必要な信号を定義
  val instr = Wire(UInt(48.W))   // 48ビットの命令
  val opcode = Wire(UInt(5.W))   // 命令の5ビットのオペコード
  val opcode_sub = Wire(UInt(3.W)) // サブオペコード
  val rd = Wire(UInt(5.W))       // 目的レジスタ
  val rs1 = Wire(UInt(5.W))      // ソースレジスタ1
  val rs2 = Wire(UInt(5.W))      // ソースレジスタ2
  val imm = Wire(UInt(32.W))     // 即値

  // Fetchフェーズ：命令をメモリから取得する
  instr := Cat(
    (0 until 6).map(i => mem.read(pc + i.U)).reverse // メモリから6バイト（48ビット）を逆順で連結
  )
  pc := pc + 6.U // プログラムカウンタを6バイト分進める

  // Decodeフェーズ：命令を分解し、オペコードやレジスタのIDを取り出す
  val command = Wire(UInt(8.W))  // ALUに渡すコマンドを保持

  opcode := instr(4, 0)          // オペコードは命令の下位5ビット
  opcode_sub := instr(7, 5)      // サブオペコードは次の3ビット
  rd := instr(12, 8)             // 目的レジスタID
  rs1 := instr(17, 13)           // ソースレジスタ1のID
  rs2 := instr(22, 18)           // ソースレジスタ2のID
  imm := instr(47, 16)           // 上位32ビットを即値として取り出す

  // デコード結果に基づいてALUに渡すコマンドを決定
  command := MuxCase(0.U(8.W), Seq(
    (opcode === 1.U(5.W) && opcode_sub === 1.U(3.W)) -> (1.U(8.W)),  // add命令
  ))

  // Executeフェーズ：ALUにデータを渡し、命令に基づいて計算を行う
  val alu = Module(new Alu) // ALUのインスタンス化

// ALUのコマンドに命令に基づいたコマンドを送信
alu.io.command := command

// ALUの入力aは、レジスタファイルからrs1レジスタの値を読み込み設定
alu.io.a := regfile(rs1)

// ALUの入力bは命令の種類に応じて、レジスタの値か即値を選択
alu.io.b := MuxCase(0.U(32.W), Seq(
  // add命令の場合は、ソースレジスタ2の値（regfile(rs2)）を使用
  (opcode === 1.U(5.W) && opcode_sub === 1.U(3.W)) -> regfile(rs2),
  
  // addi命令の場合は、即値（imm）を使用
  (opcode === 2.U(5.W) && opcode_sub === 1.U(3.W)) -> imm
))

// ALUの演算結果を目的レジスタ（rd）に書き込み
regfile(rd) := alu.io.out
  
}


// 1. 命令の実行フェーズの実現
// CPUの動作は、通常「フェッチ」、「デコード」、「実行」という3つの主要なフェーズに分けられます。以下にそれぞれのフェーズの役割を説明します：
// 
// フェッチ：メモリから命令を取得する段階（すでに実装済み）。
// デコード：取得した命令を解析し、どのような操作を行うべきかを決定する段階（すでに実装済み）。
// 実行：デコードされた命令に基づいて、演算やデータ処理を行う段階。この部分が今回の実装に該当します。
// この実行フェーズでは、命令で指定された演算をALUに渡し、その結果をレジスタに反映させます。これにより、CPUは加算や論理演算などの具体的な計算を行えるようになります。
// 
// 2. ALUとの連携
// この部分では、命令の一部である rs1、rs2 というレジスタからデータを読み込み、ALUに渡して計算を実行させ、その結果を再びレジスタファイルに書き戻す流れが実装されています。ALUはCPU内で算術演算や論理演算を行うユニットです。以下がその詳細です：
// 
// ALUモジュールのインスタンス化：val alu = Module(new Alu) によって、ALUを操作するためのインスタンスを作成しています。
// コマンドの送信：alu.io.command := command によって、デコードフェーズで決定された命令（加算など）をALUに送ります。
// データの送信：alu.io.a := regfile(rs1) と alu.io.b := regfile(rs2) によって、レジスタファイルから読み込んだデータをALUに送信します。これにより、ALUは指定された演算を実行するための2つのデータを受け取ります。
// 3. 演算結果のレジスタファイルへの書き戻し
// ALUが計算した結果（例えば、加算の結果）は alu.io.out に出力されます。次に、この結果を regfile(rd) に書き込みます。rd は目的レジスタを表しており、命令で指定された場所に計算結果を保存します。これにより、次の命令でその結果を使用することができます。
// 
// regfileへの書き込み：regfile(rd) := alu.io.out により、ALUの演算結果を目的レジスタに保存します。これにより、命令の実行結果がシステムに反映され、次の操作で使用可能となります。
// 実装の効果
// この実装により、以下の効果が得られます：
// 
// 命令の実行プロセスが完了：フェッチ、デコード、そして実行という3段階の処理が揃い、CPUとしての基本的な動作が完結します。これにより、実際のデータ処理や計算が行われるようになります。
// 計算結果の利用：ALUで行われた計算結果が正しくレジスタに書き込まれるため、次の命令でその結果を使用できるようになります。これがなければ、計算結果は捨てられてしまい、CPUとして有効な処理が行われなくなります。
// 柔軟性の向上：この実行フェーズにより、異なる命令セット（例えば、加算だけでなく減算やビット演算など）をALUに追加することが容易になります。演算の種類が増えても、この流れを基に拡張できます。
// 

