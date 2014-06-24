namespace MyNamespace {

  fn aFunction<T, U : String, V>(first : Item<T>, second : Int32) {
    val item : Int32 = 10
    muta number
    val third = 20

    muta generic : T

    #[Hashtag<T>]
    @[myParam : Aspect(param)]
    fn internalFunction(param : String) {
      val another = 10
      muta number = 20
    }

    class MyClass : String {
      muta number : Int64

    }
  }
};