

# Impact Faible

* Replace this if-then-else statement by a single return statement. [java:S1126]
* Remove redundant visibility modifiers from the methods of this test class. [java:S5786]
* File path "src\main\java\tn\esprit\autoloc\web\dto" should match package name "tn.esprit.autoloc.web". Move the file or change the package name. [java:S1598]
* Rename this generic name to match the regular expression '^[A-Z][0-9]?$'. [java:S119]


# Impact Moyen


* Rename this bean to match the regular expression '^[a-z][a-zA-Z0-9]*$'. [java:S6830]
* Define a constant instead of duplicating this literal "Agence" 3 times. [java:S1192]
* Add a private constructor to hide the implicit public one. [java:S1118]
* Explicitly specify the time zone by passing a ZoneId or a Clock to the .now() method. [java:S8688]
* Add at least one assertion to this test case. [java:S2699]

# Impact Important

* Replace this use of System.out by a logger. [java:S106]
* Replace this persistent entity with a simple POJO or DTO object. [java:S4684]
* Make sure this debug feature is deactivated before delivering the code in production. [java:S4507]
