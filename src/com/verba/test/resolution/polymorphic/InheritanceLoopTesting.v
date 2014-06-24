### Test to see if there are inheritance loops in class.

class LowerClass : UpperClass
class MiddleClass : LowerClass
class UpperClass : MiddleClass
