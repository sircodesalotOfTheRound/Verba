fn explicitString() : String {

}

fn implicitUnit() {

}

fn implicitInt32() {
    ret 10
}

fn implicitString() {
    ret "Something is a mist"
}

fn returnedLocalString() {
    val anItem = "This is a string"

    ret anItem
}

fn returnedLocalInteger(number : Int32) {
    ret number
}

fn returnChainResolvedValue(number : Int32) {
    # Todo: extend the chain to multiple items.
    val chain = number

    ret chain
}

