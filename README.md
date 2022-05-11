TWProiect-Skill-Development

-functionalitatile de schimbare de nume, email, parola (de la pagina de setari)

-functionalitatea de schimbare a fotografiei de profil (pagina de profil)

-schimbarea fotografiei din dreapta sus de la header

-file upload

-adaugarea unui microserviciu in sectiunea de "Add skill" (sectiune ce desemneaza skill-ul la care vrea sa participe utilizatorul)

-adaugarea unui skill favorit (adica cand dai pe emoticonul "ador" de pe pagina de home sa iti apara pe pagina de profil skill-ul favorit)

-m-am ocupat de cele 2 microservicii (first aid si cooking)

-baza de date pentru microserviciile mele

-baza de date pentru useri

-partea de login, sign up, cu hash la parola (am folosit AJAX si bcrypt aici), fara framework-uri

-listener pentru dupa ce te deloghezi

-autorizare si permisiunile in aplicatie

-error handling, atat in html cat si in json

-creare baze de date pentru template-ul de articol astfel incat un articol sa fie orientat obiect, si resursa sa poata fi partajata intre servicii prin JSON-uri apeland rest API-uri, ceea ce ofera aplicatiei modularitate, rezilienta si decuplare

-JSTL pentru afisarea articolelor

-Rest API-urile aplicatiei si agregarea lor in pagini (fara framework-uri)

-un daemon pentru trimis mail atunci cand un microserviciu este updated

-comunicare asincrona prin protocolul AMQP, prin brokerul de mesaje RabbitMQ, pentru mai multe topic-uri. un broker de mesaje poate oferi si mai multa decuplare intre microservicii

-containere docker, pentru fiecare baza de date si volumele pentru a persista datele, docker-compose, definirea unui docker network (pentru domenii de nume)

-sistem reactiv cu push notifications pentru utilizatorii inscrisi la un anume skill (tehnologii: AJAX, RabbitMQ)

-interactiunea cu rest api-urile private ale microserviciilor printr-o interfata grafica, disponibila doar utilizatorilor ce sunt administratori

-sistem de testare, unde un utilizator poate primi achievements daca raspunde corect la intrebari (AJAX)

-cronjob pentru o situatie imaginara in care articolele sunt puse spre revizuire

-protectie impotriva XSS

-documentatia openAPI

-documentatie Scholarly si diagrame

