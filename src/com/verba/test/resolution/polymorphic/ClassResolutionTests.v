### This is a comment here
### This is another comment here

trait FirstTrait {
    val firstTraitEntry = 10

    fn myFunction() { }
}

trait SecondTrait {
    val secondTraitEntry = 20

    fn anotherFunction() {
        val notVisibleInClassScope = 30
    }
}

trait ThirdTrait : SecondTrait {
    val thirdTraitEntry = 10

    fn thirdFunction() {

    }
}

class SubClass : ThirdTrait {
    #|Hashtag<String>("Something is here")
    fn functionInAClass() {

    }
}

### This will read the traits recursively.
### That is, it will read third trait and then all
### Traits that it implements.

class MyClass : SubClass, FirstTrait {
    val first = 1
    val second = 2
    val third = "Something"
}
