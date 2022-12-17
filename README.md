# Hacked: геолокационная игра

## Описание проекта

Hacked － геолокационная игра на хакерскую тематику. Главный герой является хакером, который бросил вызов крупным корпорациям. Игроку необходимо перемещаться на реальной местности, искать объекты инфраструктуры разных типов (коммерческие, гражданские, культурные) для их взлома.

Путешествуя по окружающему миру, игрок будет взламывать объекты на карте при помощи мини-игры. За успешный взлом повышается опыт (и уровень) и даётся случайное количество внутриигровой валюты.

## Скриншоты

<div>
  <img src="https://github.com/gleb-novikov/hacked/blob/master/media/screenshot_1.png" width="32%" height="32%">
  <img src="https://github.com/gleb-novikov/hacked/blob/master/media/screenshot_2.png" width="32%" height="32%">
  <img src="https://github.com/gleb-novikov/hacked/blob/master/media/screenshot_3.png" width="32%" height="32%">
</div>

## Структура

Проект реализован по паттерну Single Activity с использованием Fragments и навигацией через библиотеку Navigation.

* В корневом пакете находятся классы Fragments
* В пакете **configs** находится конфиг с константами проекта
* В пакете **geodesy** находится класс для геодезических расчётов на карте
* В пакете **hacking** находятся классы для мини-игры, созданной на Canvas
* В пакете **retrofit** находятся классы и интерфейсы для взаимодействия с API backend-а

## Библиотеки

Проект построен, с использованием таких библиотек, как:

1. [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - для навигации между экранами приложения
2. [Retrofit](https://square.github.io/retrofit/) - для сетевых запросов
3. [Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk/overview) - для отображения карты и определения местоположения пользователя
4. [Toasty](https://github.com/GrenderG/Toasty) - для кастомизированных Toasts

## Build

Для сборки проекта необходимо в корне проекта создать файл **local.properties** с указанием переменной окружения с ключём для Google Maps:

`
MAPS_API_KEY=AIza...
`

## Backend

Backend приложения написан на Node.js с базой данных MongoDB и развёрнут на VPS с помощью Docker.
API предоставляет функционал для авторизации, регистрации и изменения пользовательских данных.

* [GitHub Backend](https://github.com/Tinkerbells/hacker-app-back)
* [API в Postman](https://www.postman.com/gleb-no/workspace/hacked/collection/11215862-05162e21-8e12-4cd4-a49f-2388c87d3452?action=share&creator=11215862)
