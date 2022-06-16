package boot.spring.entity

class TResult(var code: Int=400, var msg: String="", var data: Any?=""){
    constructor() : this(400, "", "")
}
