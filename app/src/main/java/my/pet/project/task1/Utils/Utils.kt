package my.pet.project.task1.Utils



object Utils {
    const val EMAIL_PATTERN = "^" +
            "([a-z0-9_\\.-]+)" +    //several letters a-z, digits, _, - and .
            "@" +                   //@
            "([a-z0-9_\\.-]+)" +    //several letters a-z, digits, _, - and .
            "\\." +                 //.
            "([a-z]{2,6}\\.?)" +    //domain zone (several letters a-z repeats 2-6 times and . )
            "$"


    const val PHONE_PATTERN = "^\\+\\d \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}"
}