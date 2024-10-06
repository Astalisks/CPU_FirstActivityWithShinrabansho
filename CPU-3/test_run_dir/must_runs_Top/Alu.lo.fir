FIRRTL version 1.1.0
circuit Alu :
  module Alu :
    input clock : Clock
    input reset : UInt<1>
    input io_command : UInt<8>
    input io_a : UInt<32>
    input io_b : UInt<32>
    output io_zero : UInt<1>
    output io_out : UInt<32>

    node _io_zero_T = eq(io_out, UInt<32>("h0")) @[Alu.scala 16:22]
    node _io_out_T = eq(io_command, UInt<8>("h1")) @[Alu.scala 21:17]
    node _io_out_T_1 = add(io_a, io_b) @[Alu.scala 21:40]
    node _io_out_T_2 = tail(_io_out_T_1, 1) @[Alu.scala 21:40]
    node _io_out_T_3 = eq(io_command, UInt<8>("h2")) @[Alu.scala 24:17]
    node _io_out_T_4 = sub(io_a, io_b) @[Alu.scala 24:40]
    node _io_out_T_5 = tail(_io_out_T_4, 1) @[Alu.scala 24:40]
    node _io_out_T_6 = mux(_io_out_T_3, _io_out_T_5, UInt<32>("h0")) @[Mux.scala 101:16]
    node _io_out_T_7 = mux(_io_out_T, _io_out_T_2, _io_out_T_6) @[Mux.scala 101:16]
    io_zero <= _io_zero_T @[Alu.scala 16:11]
    io_out <= _io_out_T_7 @[Alu.scala 19:10]
