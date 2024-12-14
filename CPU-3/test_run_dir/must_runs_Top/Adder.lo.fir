FIRRTL version 1.1.0
circuit Adder :
  module Adder :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<8>
    input io_b : UInt<8>
    output io_out : UInt<8>

    node _io_out_T = add(io_a, io_b) @[Adder.scala 12:18]
    node _io_out_T_1 = tail(_io_out_T, 1) @[Adder.scala 12:18]
    io_out <= _io_out_T_1 @[Adder.scala 12:10]
