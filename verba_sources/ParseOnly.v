
// Importing namespaces
withns vm.some_namespace

// Some basic functionality
namesapce a_namespace {

  #[HashTag]
  #[GenericHashtag<String>]
  #[HashtagWithLambda(x -> x)]
  @[SimpleAspect]
  @[named_aspect : SomeAspect]
  @[newed_aspect : new NewedAspect]
  @[injected_aspect : inject NewedAspect]
  fn tagged_up_function { }

  #: Some simple functions:
  fn an_empty_function
  fn paramaterized_function(string_parameter : utf) {
   print(string_parameter)
  }

  // Some polymorphics
  trait MyTrait
  class SimpleClass
  class DerivedClass : SimpleClass, MyTrait
  class InlineClass(first_name : utf, last_name : utf, age : int)

  class MuliContructorClass {
    val member_variable : int
    public Constructor(value : int) {
      member_variable = value
    }

    public Constructor(value : int, string : utf) {
      member_variable = int
      print(string)
    }
  }

  #: Object Types
  fn my_function() {
    val type_inferred_literal = 42
    val explicit_type : object = new Object()
    val mut mutable_literal = "welcome"
    val lambda = (lhs, rhs) -> lhs + rhs

    val dependency = inject MyTrait

    val array = [ 1, 2, 3 ]

    // Async call to empty nested function
    fn nested_function
    val future_value = async nested_function()
    return future_value
  }
}

markup MyMarkup {
  <root>
    <parent>
      <child/>
    </parent>
    <node />
  <root>
}
