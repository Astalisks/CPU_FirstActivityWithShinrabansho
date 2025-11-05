FIRRTL version 1.2.0
circuit Adder :
  module Adder :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<8> @[\\src\\main\\scala\\Adder.scala 6:14]
    input io_b : UInt<8> @[\\src\\main\\scala\\Adder.scala 6:14]
    output io_out : UInt<8> @[\\src\\main\\scala\\Adder.scala 6:14]

    node _io_out_T = add(io_a, io_b) @[\\src\\main\\scala\\Adder.scala 12:18]
    node _io_out_T_1 = tail(_io_out_T, 1) @[\\src\\main\\scala\\Adder.scala 12:18]
    io_out <= _io_out_T_1 @[\\src\\main\\scala\\Adder.scala 12:10]
