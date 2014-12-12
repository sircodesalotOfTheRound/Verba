Verba
=====

The verba language compiler.

Compiling
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

# Current Status
  

# Roadmap
The following are the primary features that will be implemented by version 1.0.

## Functions
Unlike Java or C#, functions don't need to be methods. Instead, functions can belong to namespaces directly

```
# Verba Code:
fn empty_function
fn
```


### Async
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

## Static Evaluation, Dynamic Evaluation
```
val array_from_literals = [1, 2, 3, 4]
val array_from_range = [ 1 to 100 ]
val array_from_span = [
  ["Tom", "Dick", "Harry", "Robert"]
    .where(number -> number % 2 == 0)
    .map(number -> number * 2)
]
```

```
fn my_function {
  # The 'd' is silent
  val djson = {
    value : "First",

  }

  # Append a new property to the json object.
  djson.property = "something"

  # Use the 'hasa' operator to test for membership.
  if (djson hasa property) {

  }
}
```

### Polymorphic Reflection
* `is` tests object references, similar to java (`==`).
* `isa` tests whether one object derivation.
* `hasa` tests membership (useful for dynamic json).

### Extended Operators:

* `a !< b` (`a` is not less than `b`)
* `a !is b` (`a` is not the same object as `b`)
* `a !isa b` (`a` does not derive from `b`)

### Meta Programming:
```
meta MyMeta<T, U, V> {
  fn my_function(name : utf) {
    print("This will print out a ${@name}")
  }
}
```

### Markup:
Markup is used a a simple interface for building tree structures:

### Hashtags and Aspects
A hashtag is similar to Java `Annotations` or C# `Attributes`. A hashtag effectively provides meta information about a particular function.

```
  #: This is a hashtag.
  #[Obsolete]
  fn now_marked_as_obsolete

```

Like C# Hashtags are clases which derive from `Hashtag`, which means they can contain methods as well as be otherwise polymorphic (like any other type).

```
class MyHashtag : Hashtag {
  fn member_function() {
    print ("This is a ${this.def.name}")
  }
}
```

Imrpoving on C#, however, `Hashtags` can be generic. They can also contain lambdas (but not closures).

```
class AFunction {
  #[MyHashtag()]
  fn my_function() { }
}
```

An aspect is a segment of code that is executed before and after a function:
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

Where the transaction
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



## Program Flow

```
val ternary_ish =
  if (one): true
  otherwise: none
```

### Options:

### Templates:
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

Notice that templates can be `Hashtagged` with format information so that IDEs can provide code completion assistence.

```
fn a_template {
  template {

  }
}
```

### Operator overloading:

Verba will support operator overloads, including C++ style functors.

### Inline Sql:
Everybody uses sql. Unfortunately, most languages treat sql as plain strings. The verba language uses a special sql type that allows
```
val some_sql = sql {
  from my_table
  select first_name, last_name
}

sql a_query(name : utf) {
  from people
  inner join purchases where people.name =
  select
  where name = @name
}
```