trait Fighter {
    fn fight() { }
}

trait Lover {
    fn love() { }
}

trait Person {
    val age = 10
}


### Create a class that implements both traits
class Roles : Fighter, Lover, Person

