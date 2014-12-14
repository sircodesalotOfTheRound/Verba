Verba
=====

The verbatim byte literature compiler for the verba language. To see what you can use now, check out the **<a href="https://github.com/sircodesalotOfTheRound/Verba/wiki">samples of verba language currently implemented features</a>**!

Building
---------
To build the Verba language compiler, simply copy and paste the following commands:

```
# Clone from repository.
git clone https://github.com/sircodesalotOfTheRound/javaLinq.git
git clone https://github.com/sircodesalotOfTheRound/Verba.git
git clone https://github.com/sircodesalotOfTheRound/Verbalize.git

# Build 
cd javaLinq; mvn clean install; cd..
cd Verba; mvn clean install; cd ..
cd Verbalize; mvn clean install; cd ..
```

> **Note** Make sure that your maven is configured to use Java 1.8.
> ```
> $ mvn -v

> Apache Maven 3.2.3 (33f8c3e1027c3ddde99d3cdebad2656a31e8fdf4; 2014-08-11T15:58:10-05:00)
> Maven home: /usr/local/Cellar/maven/3.2.3/libexec
> Java version: 1.8.0-ea, vendor: Oracle Corporation
> Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0.jdk/Contents/Home/jre
> Default locale: zh_CN, platform encoding: UTF-8
> OS name: "mac os x", version: "10.10.1", arch: "x86_64", family: "mac"

> ```


# Compiler Overview
The verba language uses a hand-crafted backtracking recursive descent parser. Hand crafted parsers allow for greater
flexibility when parsing context-sensitive content. Context-sensitive content allows the language to be more expressive.

## Basic architecture:
>* The **verba** font-end compiler analyzes and emits verbatim byte code literature.
>* The **verbalize** build system generates, organizes and deploys verbatim byte code lit files.
>* The **verbOS** runtime is used to load and execute verbatim byte code literature.
>* The **verbaj** just-in-time compiler translates byte code lit into architecture specific instructions.

# Roadmap
The following are the primary features that will be implemented by version 1.0.

## Functions
Unlike Java or C#, functions don't need to be methods. Instead, functions can belong to namespaces directly

```
# Verba Code:
class AClass {
  fn member_method() { }
}

fn non_class_function() {

}

fn function_with_explicit_return_type : AClass { ... }
fn generic_function<T> { }

```

Also, Functions in verba always return a value. Functions that return "no" value return `unit`. This simplifies asynchronous and functional programming.

## Async
The `async` keywords changes a function from syncrhonous to asynchronous:
```
fn lengthy_io_process {
  # ...
}

fn starter {
  async lengthy_io_process()
}
```

This works similar to the `async`/`await` pattern in C#. The key value add is that functions don't need to be pre-declared as asynchronous functions, because verba functions never return void, and thus always return *something* (even if it's just `unit`). For example:

## Namespaces
The verba language will use C++/C# style name spacing.

## Generics
Verba is build designed from the ground up to implement reified generics. Finally, a fully open source language with true generic capabilities.

## Collections and Data transforms

Verba natively supports the `Array<T>` data type:

```
val array_from_literals = [1, 2, 3, 4]
val array_from_range = [ 1 to 100 ]
```

Arrays, and all collections derive from the `Span<T>` base trait. A `Span<T>` is similar to the idea of an `Iterable<T>`. However, when a user imports the `vm.data` namespace, they will import a set of class extensions that allow for chained data transforms such as `where`, `map`, `distinct`, etc. To get a sense for what types of functions will be supported, see my <a href="https://github.com/sircodesalotOfTheRound/javaLinq">javaLinq set transforms api</a>:

```
withns vm.data

#: Take items from an existing array,
#: filter out only those names that have even length,
#: then map those names into a named tuple.
val array_from_span = [
  ["Tom", "Dick", "Harry", "Robert"]
    .where(name -> name.length() % 2 == 0)
    .map(name -> (name: name, length: name.length()))
]
```

## Named Tuples

Tuples are excellent for those situations where you want to return two values at once. However, tuples are more useful when the values can be retrieved by *name* rather than by *index*:

```
fn polar_to_cartesian(distance : int, angle : decimal) : (x: decimal, y: decimal) {
  val x = ...
  val y = ...
  
  return (x, y)
}
```

Verba uses named tuples to allow easy collation of values into a data structure.

## Class extensions
Using the `extend` keyword, a class can be re-opened for method modification. For example:

```
extend List<T> {
  fn distinct_by_property<U>(on_property : T -> U) { ... }
}

val people_with_distinct_names = new List<Person>()
  .distinct_by_property(person -> person.name)
  
```

Here, the `List<T>` class is re-opened for extension, adding the method `distinct_by_property`, which allows collections to be filtered for distinct items based on some projection.

## Static Evaluation, Dynamic Evaluation
By default, verba is a statically typed language. This allows for better performance, and application scalability. However, there are certain scenarios when dynamic execution is neccesary. Verba supports dynamic execution in two ways.

### Dynamically Typed Objects and Parameters
A dynamically typed parameter is one of two things:

> * (1) A value declared with `val` that has an explicit `dynamic` type:
> ```
>  # An explicity dynamic reference.
>  val a_dynamic_string : dynamic = "The quick brown fox"
> ```
> * (2) A parameter without an explicit type:
> ```
> # A paramter which is dynamic because its type is left unspecified.
> fn function(int_parameter : int, dynamic_parameter) { ... }
> ```

Dynamic parameters in and of themselves don't change the **behavior** of the underlying type. A `utf` string is still a string, and an `int` is still and int -- making them dynamic will not change that. What dynamic typing does, is tell the underlying execution environment that every operation performed on that type/reference should be done dynamically. This has important implications for the `json` object type:

```
fn my_function {
  # The 'd' is silent
  val djson = {
    first: 1,
    second: "two",
    third: { inner: new Set<String>() }
  }

  # Append a new property to the json object.
  djson.property = "something"

  # Use the 'hasa' operator to test for membership.
  if (djson hasa property) {
    ...
  }
}
```

### The Json type

The `json` type is a dynamic, heterogenous collection. Think of it as an advanced, first-class `ExpandoObject` (C#). In other words, `json` types can be modified at run-time, adding and changing the properties and values associated with it. The beauty of this is that when you need a dynamic object, it's there for you, and you can use dynamic typing to allow verba to switch execution to dynamically determine what options can be performed on the object. 

Compare this with trying to work with `json` ingested from say, MongoDB or some rest service, and having to modify it using a `Map<Key, Value>` (Java)/ `Dictionary<Key, Value>` (C#). Here, you have type safety for building things out with compiler enforced determinism, but dynamic execution for those moments you really do need it.

### Polymorphic Reflection
* `is` tests object references, similar to java (`==`).
* `isa` tests whether one object derivation.
* `hasa` tests membership (useful for dynamic json).

## Extended Operators:

* `a !< b` (`a` is not less than `b`)
* `a !is b` (`a` is not the same object as `b`)
* `a !isa b` (`a` does not derive from `b`)

## Meta Programming:
The verba compiler can emit meta code during compilation. Think of this as a more beefed up version of C++ templates:

```
meta MyMeta (os : OperatingSystem) {
  fn execute_based_on_OS(os : OperatingSystem) {
    @if (os == OperatingSystem.Mac) {
      print("this is running on a mac")
    } @else if (os == OperatingSystem.PC) {
      print("this is running on a PC")
    }
  }
}

val incrementer = resolve MyMeta(OperatingSystem.Mac)
```

The `meta` keyword allows the developer to emit any single `class`, `function` or other verba type that is then templated at runtime using the `resolve` keyword (just as a C++ function is templated at compile time). Using the `@` meta key, allows the developer to access values from outside of the meta scope. In the example above, we use a meta `@if` statement to determine what type of operating system is currently running. This `@if` statement runs *once*, and then emits compiled code accordingly. Another example:

```
fn prime_calculator(nth_prime : int) { ... }

meta PrimeNumber(nth_prime : int) {
  fn prime_function_for_@{nth_prime}() {
    return @prime_calculator(nth_prime)
  }
}
```

Here, when the `meta` function `PrimeNumber` is instantiated, it calls the `prime_calculator` function and then bakes in the resulting value. Once the instantiated function is used, it will always immediately return the value associated with the prime. Meta programming can be used to easily create new classes and functions that correspond to the immediate runtime environment.

## Markup:
Markup is used a a simple interface for building tree structures:

```
markup MyWindowDefinition {
  <Window>
    <Frame>
      <Label text="This is the text I want to show" />
    </Frame>
  </Window>
}
```

Markup is not standard XML, however, as it allows for naming elements, as well as usign the `@` meta character to evaluate values before they are applied to an attribute:

```
markup MyWindowDefinition(button_click_message: utf) {
  # Name the window "window".
  <window: Window>
    
    # Use the meta character (@) to apply a function callback to the button tag.
    # When the button is clicked, the title is set to the value of the 'button_click_message'.
    <Button on_click=@fn { 
      window.title = button_click_message
    }/>
    
  </Window>
}
```

## Hashtags and Aspects
A hashtag is similar to Java `Annotations` or C# `Attributes`. A hashtag effectively provides meta information about a code object (such as a function, class, or trait).

```
  #: This is a hashtag.
  #[Obsolete]
  fn now_marked_as_obsolete

```

Like C# `Hashtags` are clases that derive from the `Hashtag` base class, which means they can contain methods as well as be otherwise polymorphic (like any other type).

```
class MyHashtag : Hashtag {
  fn member_function() {
    print ("This is a ${this.def.name}")
  }
}
```

Imrpoving on C#, however, `Hashtags` can be generic. They can also contain lambdas (but not closures).

```
class SomeClass {
  #[ValueProcessor(value -> value.length())]
  val some_value : utf
}
```

An `Aspect` tag is a segment of code that is executed before and after a function begins and exits. Think of it as a simple function execution contextualizer/container:
```
#: Notice that transactions can capture parameters from the function signature
#: As well as from class members.

class PersonInserter {
  priavate sql insertion_sql(first : utf, last : utf, age : int) {
    insert into person_table(FIRST, LAST, AGE)
    values (@first, @second, @third)
  }

  @[Transaction(connection)]
  fn block_in_a_transaction(connection : DbConnection) {
    val result = connection.execute(insertion_sql)
    return result
  }
}
```

Where the transaction is defined as:
```
class Transaction {
  val transaction = new DatabaseTransaction

  public Transaction(connection : DbConnection) {
    this.transaction = connection.open_transaction()
  }

  public override fn on_enter
  public override fn on_exit() { transaction.close() }
  public override fn on_exception(ex : Exception) {
    log.write("Yup, something just broke")
    log.write(error_string)
    transaction.abort()
  }
}
```

As you can see, `Aspects` define three virtual functions, `on_enter` (called before the function executed), `on_exit` (called before the function exits), and `on_exception` (called when an exception is thrown). These functions can be used to provide a reusable execution context container for a given function.

## Templates:
Templates are modifiable text blocks inspired by Asp.net MVC Razor. Whereas razor only works on dedicated razor HTML markup files, templates can be used anywhere:

```
#Template.Format<Template.Formats.HTML>
template Header(title : utf) {
  <head>
    <title>@title</title>
  </head>
}

#Template.Format<Template.Formats.HTML>
template Body(content : template) {
  <body>
    @content
  </body>
}


#Template.Format<Template.Formats.HTML>
template WelcomeMessage(message) {
  <html>
    @new Header("The title for this website")
    @new Body("Welcome to my awesome website.")
  </html>
}
```

Notice that templates can be `Hashtagged` with format information so that IDEs can provide code completion assistence. In the example above, `#Template.Format` is generic of type `Template.Formats.HTML`. Using this, an IDE could determine that the encased text is HTML, and provide the developer code assistence.

## The Sql Type:
Everybody uses sql. Unfortunately, most languages treat sql as plain strings. The verba language uses a special sql type that effectively converts ansi compatible sql into an expression tree that can be interpreted by a database connector.

```
val some_sql = sql {
  from my_table
  select first_name, last_name
}

public sql AQuery(name : utf) {
  from people
  join purchase on people.id = purchase.id
  select first_name, last_name, age
  where name = @name
}

val database_connector = new DbConnector
val query = new AQuery("Peter")
val query_results = database_connector.query(query)

for (val result : query_results) {
   ... some sort of code here
}
```

Notice that the meta character `@` is used with the value `@name` here to express that it's value should be captured externally (either by closing over the value from a surrounding function/class or by capturing it from the parameter list as shown here). Since the entire `sql` expression is emitted into the assembly package as an expression tree, the receiving database connector could choose to parameterize `@name` preventing sql injection attacks.
