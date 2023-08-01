// 2.9.2 다중 조건 선택 사용하기

String country = ...
String capital;

switch(country) {
    case "Australia":
        capital = "Canberra";
        break;
    case "Bolivia":
        capital = "Sucre";
        break;
    case "Brazil":
        capital = "Brasilia";
        break;
    default:
        capital = "Unknown";
}


val country = ...

val capital = when (country) {
    "Australia" -> "Canberra"
    "Bolivia"   -> "Sucre"
    "Brazil"    -> "Brazilia"
    else        -> "Unknown"
}


val country = ...

val capital = when {
    tired                  -> "Check for yourself"
    country == "Australia" -> "Canberra"
    country == "Bolivia"   -> "Sucre"
    country == "Brazil"    -> "Brazilia"
    else                   -> "Unknown"
}