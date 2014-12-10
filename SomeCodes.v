fn something_else {
  return "something"
}

public fn my_function() {
  val something = "something"
  val otherthing = something_else(something)
  val value = true

  return otherthing
}