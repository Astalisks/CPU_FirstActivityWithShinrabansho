FIRRTL version 1.1.0
circuit Alu :
  module Alu :
    input clock : Clock
    input reset : UInt<1>
    input io_command : UInt<8>
    input io_a : UInt<32>
    input io_b : UInt<32>
    output io_out : UInt<32>

    node _io_out_T = eq(io_command, UInt<8>("h1")) @[Alu.scala 16:17]
    node _io_out_T_1 = add(io_a, io_b) @[Alu.scala 16:40]
    node _io_out_T_2 = tail(_io_out_T_1, 1) @[Alu.scala 16:40]
    node _io_out_T_3 = mux(_io_out_T, _io_out_T_2, UInt<32>("h0")) @[Mux.scala 101:16]
    io_out <= _io_out_T_3 @[Alu.scala 15:10]
