# Лабораторна робота 1 

**Студент:** Жила Іван, група ІМ-42, номер у списку групи — 12.

## Опис проєкту
Система управління замовленнями в стилі DOP (Java 25):
- `OrderStatus` — sealed interface; його реалізації `Pending`, `Paid`, `Shipped`, `Cancelled` — незмінні records із валідацією в компактних конструкторах;
- `OrderProcessor` — окремий клас-обробник: switch expression з pattern matching (без `default`) повертає опис статусу у вигляді text block.

Усі класи знаходяться в пакеті `ua.kpi.comsys.dop`.

## Розрахунок варіанта
V = N mod 3, де N = 12 (порядковий номер у списку групи).
V = 12 mod 3 = **0** → «Система управління замовленнями».

## Структура
```
src/main/java/ua/kpi/comsys/dop/   OrderStatus, Pending, Paid, Shipped, Cancelled, OrderProcessor
src/test/java/ua/kpi/comsys/dop/   OrderProcessorTest, OrderStatusValidationTest
```

## Вимоги
- JDK 25 (змінна JAVA_HOME має вказувати на нього)

## Запуск тестів
Windows:
```
.\mvnw test
```
Linux / macOS:
```
./mvnw test
```
Звіт про покриття (JaCoCo): `target/site/jacoco/index.html`.