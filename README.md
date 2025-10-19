
Сборка проекта:
mvn clean package

Запуск проекта (Выделена дополнительная память, чтобы не возникало ошибок):
java -Xms2g -Xmx12g -jar target/benchmarks.jar


Чтобы читать результаты нужно смотреть на строки:

Iteration   1: 5010,091 ms/op

Одна операция (@Benchmark-метод) заняла в среднем 5010,091 миллисекунд. 

в конце блока вы увидите усреднённое значение:

Result "org.example.stats.ActiveUsers.ActiveUsersBenchmark.parallelCustom":
5010,1 ?(99.9%) 10,2 ms/op [Average]
(min, avg, max) = (5008, 5010, 5012)

число avg и есть итоговое время выполнения метода

Результаты:
https://docs.google.com/spreadsheets/d/1YQtn9SlXpid_3mdb-ljrFWvucT7zg3RhqUgDLJdb3ag/edit?gid=0#gid=0