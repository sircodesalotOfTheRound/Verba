# A function that returns unit
fn function: unit { }

class SimpleClass { }

# A function which returns 'two'
fn two : unit64 { return 1 + 1 }

# A function that returns "The quick brown fox"
fn quick_brown_fox { return "The quick brown fox" }

# A function that return a named value.
fn named_value {
  val ten = 10
  return ten
}
