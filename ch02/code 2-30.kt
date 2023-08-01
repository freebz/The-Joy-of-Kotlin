// 2.14 문자열 인터폴레이션

System.out.println(String.format("%s's registration date: %s", name, date));


println("$name's registration date: $date")


println("$name's registration date: ${date.atZone(ZoneId.of("America/Los_Angeles))}")