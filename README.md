# StarWars
Данный проект создан в рамках тестового задания на должность Android-разработчика.

## Описание приложения
Данное приложение позволяет пользователям:
+ искать персонажей, звездолёты и планеты из вселенной "Звёздных воин";
+ сохранять их в избранном;
+ просматривать избранную коллекцию, в которой также отображаются фильмы, где появляются любимые персонажи, звездолёты и планеты.

## Функциональность приложения
На стартовом экране отображён компонент поиска и нижняя навигационная панель с двумя пунктами назначения: поиск и избранное.
При первом запуске или когда коллекция избранного пуста, на соответствующем экране отображается сообщение об этом.
Поиск начинается сразу после ввода пользователем хотя бы двух символов, после чего осуществляется запрос на https://swapi.dev/, откуда подгружаются необходимые ресурсы и отображаются в результатах поиска.
При нажатии пользователем на кнопку "поиск" клавиатуры, панел поиска сворачивается, а текущие результаты отображаются на главной странице.

## Стек
+ Kotlin
+ Jetpack Compose
+ Retrofit
+ Kotlin Serialization
+ Proto DataStore
