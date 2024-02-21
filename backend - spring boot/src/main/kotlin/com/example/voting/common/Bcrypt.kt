package com.example.voting.common

object Bcrypt{
    private const val salt : String = "23edyhr8n734irh].;po320n8d32h4h34xi2u3mqdf'cx;p[.kr,co;0,-439"
    val hash = fun(password : String) : String {
        var hash: String = "";

        val passwordWithSalt = StringBuffer()

        for (c in password.indices) {
            passwordWithSalt.append(password[c])
            passwordWithSalt.append(salt[c])
        }

        for (i in passwordWithSalt.indices) {
            var c: Char = passwordWithSalt[i]
            if (i % 2 == 0) {
                c = if (c.code == 122) (c.code - 25).toChar()
                else {
                    (c.code + 1).toChar()
                }
                passwordWithSalt.append(c)
            } else passwordWithSalt.append(c)
        }
        hash = passwordWithSalt.toString()
//        println("HASH IS : $hash and the PASSWORD IS : $password")
        return hash;
    }

    val compare = fun(password: String, hash : String) : Boolean = hash(password) == hash;

}