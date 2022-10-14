xslt, полнотекстовый	 поиск

Реализация полнотекстового поиска по превью новостей.
Сервис должен получать информацию с добавленных rss лент каждый час. 

Если появилась новая информация - преобразовать ее в объект 
для хранения с помощью xslt преобразований и сохранить в Elasticsearch.
Список rss лент хранится в БД. 


Необходимо реализовать ендпоинты
для добавления, удаления, получения rss лент, включения и отключения rss ленты
(признак, по которому мы определяем, необходимо ли индексировать новости с нее). 


Также необходимо реализовать ендпоинт, который будет осуществлять поиск 
новостей по имени и описанию (на вход приходит просто строка). (ELASTICSEARCH)

Этот ендпоинт должен возвращать список новостей: Заголовок, ссылку, описание.

Пример rss лент https://news.yandex.ru/index.rss, https://lenta.ru/rss

парсер должен записывать в бд
пожключить элестиксерч + сюда отправлять (создать индекс и сохранять)

1) docker-compose up
2) You can use spring.datasource.url=jdbc:postgresql://127.0.0.1:5433/RSS_manager

Url	Дата добавления	Дата обновления	Вкл/Выкл ленту
Включена	https://www.vedomosti.ru/rss/rubric/realty/architecture	2022-12-12 00:00:00	2022-12-12 00:00:00		
Включена	https://www.houzz.ru/getGalleries/featured/out-rss	2022-12-12 00:00:00	2022-12-12 00:00:00		
Включена	http://www.finmarket.ru/rss/mainnews.asp	2022-12-12 00:00:00	2022-12-12 00:00:00		
Включена	https://naavtotrasse.ru/feed	2022-12-12 00:00:00	2022-12-12 00:00:00		
Включена	http://umpako.com/feed/rss.xml	2022-12-12 00:00:00	2022-12-12 00:00:00		
Включена	https://naked-science.ru/?yandex_feed=news
http://itnews.com.ua/export/hard.rss
http://itnews.com.ua/export/technologies.rss
http://itnews.com.ua/export/all.rss

---
https://naked-science.ru/?yandex_feed=news
http://www.ixbt.com/export/softnews.rss