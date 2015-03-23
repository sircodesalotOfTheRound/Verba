#: Slight modification

# These test simple literal return value
fn returns_unit
fn returns_utf { return "Should say utf" }
fn returns_int { return 42 }


# Here 'item' is of type 'MyClass'
public class MyClass
fn returns_type(item : MyClass) { return item }

# Here, however, parameter is 'dynamic' or 'untyped'
fn returns_dyna(parameter) { return parameter }

# This returns the value of an item:
fn returns_value {
  val value = 10
  return value
}

#: This returns the value of a type-inferred chain (here: utf)
fn returns_chain {
  val first = "this is what should be returned"
  val second = first
  val third = second

  return third
}


# Polymorphism tests
class NonBase {
  val not_a_member : utf
}

class Base {
  val base_class_member = 1
}

trait BaseTrait {
  fn base_trait_function() { }
}

class Derived : Base, BaseTrait {
  fn local_function
  val local_member : utf
}


