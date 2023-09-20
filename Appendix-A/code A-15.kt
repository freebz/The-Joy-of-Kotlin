// A.3 SAM 인터페이스

// kotlin code
val executor = Executors.newSingleThreadExecutor()
executor.submit { println("Hello, World!") }


// java code
ExecutorService executor = Executors.newSingleThreadExecutor();
executor.submit(() -> System.out.println("Hello, World!"));


// kotlin code
val runnable = Runnable { println("Hello, World!") }


val executor = Executors.newSingleThreadExecutor()
val runnable: () -> Unit = { println("Hello, World!") }
executor.submit(runnable)