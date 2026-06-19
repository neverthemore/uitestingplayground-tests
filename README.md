# UI Testing Playground — автотесты UI

Проект автоматизированного UI-тестирования сайта
[UI Test Automation Playground](http://www.uitestingplayground.com/home).

**Стек:** Java 17 · Maven · Selenium WebDriver 4 · TestNG · Allure Report · WebDriverManager · SLF4J

## Что покрыто тестами

| № | Сценарий | Страница | Файл теста |
|---|----------|----------|------------|
| 1 | **Sample App** (обязательно) — успешный и неуспешный вход | `/sampleapp` | `SampleAppTest.java` |
| 2 | **Frames** (обязательно) — вложенные iframe, 4 стратегии поиска элементов | `/frames` | `FramesTest.java` |
| 3 | **Click** — «настоящий» клик Selenium против синтетического | `/click` | `ClickTest.java` |
| 4 | **Dynamic ID** — нестабильный `id`, поиск по классу | `/dynamicid` | `DynamicIdTest.java` |
| 5 | **Text Input** — обновление текста кнопки только после клика | `/textinput` | `TextInputTest.java` |

Тест №1 (Sample App) содержит 2 проверки (успех/ошибка), итого **6 тестовых методов**.

## Структура проекта

```
uitestingplayground-tests/
├── pom.xml                                  # зависимости и сборка (Maven)
├── .github/workflows/tests.yml              # CI: прогон тестов + публикация Allure-отчёта
├── src/main/java/com/uitesting/pages/       # Page Object классы
│   ├── BasePage.java
│   ├── SampleAppPage.java
│   ├── FramesPage.java
│   ├── ClickPage.java
│   ├── DynamicIdPage.java
│   └── TextInputPage.java
└── src/test/
    ├── java/com/uitesting/
    │   ├── tests/                           # сами тесты + базовый класс с setUp/tearDown
    │   └── listeners/                       # листенер для Allure environment.properties
    └── resources/
        ├── testng.xml                       # TestNG-сьют (порядок и листенеры)
        ├── allure.properties
        └── simplelogger.properties          # настройка логирования
```

Архитектура — классический **Page Object Model**: вся работа с локаторами и взаимодействием с элементами вынесена в `pages/*`, тесты содержат только сценарий и проверки.

---

## 1. Предварительные требования

Перед запуском убедитесь, что установлено:

1. **JDK 17 или новее** — проверить: `java -version`
   Скачать: https://adoptium.net/
2. **Apache Maven 3.8+** — проверить: `mvn -version`
   Скачать: https://maven.apache.org/download.cgi
3. **Google Chrome** (любая актуальная версия) — браузер, в котором реально выполняются тесты.
   Скачать: https://www.google.com/chrome/
   > ChromeDriver скачивать вручную **не нужно** — это делает библиотека `WebDriverManager` автоматически при первом запуске (нужен доступ в интернет).
4. **Git** — для клонирования репозитория.

Allure CLI устанавливать необязательно (отчёт можно собрать через Maven-плагин), но для удобного просмотра рекомендуется — см. шаг 4.

---

## 2. Как развернуть окружение

```bash
git clone <ССЫЛКА_НА_ВАШ_РЕПОЗИТОРИЙ>
cd uitestingplayground-tests
```

Дополнительных шагов установки не требуется — все Java-зависимости Maven скачает сам при первом запуске тестов.

---

## 3. Как запустить тесты

### Базовый запуск (headless — без открытия окна браузера, для CI)

```bash
mvn clean test
```

### Запуск с видимым окном браузера (удобно для локальной отладки)

```bash
mvn clean test -Dheadless=false
```

### Запуск одного конкретного теста

```bash
mvn clean test -Dtest=SampleAppTest
```

После прогона:
- Результаты сборки — в `target/`
- "Сырые" данные для Allure — в `target/allure-results/`
- Лог выполнения — прямо в консоли (формат настроен в `simplelogger.properties`)

---

## 4. Как получить и открыть Allure-отчёт

### Вариант A — через Allure CLI (рекомендуется, живой интерактивный отчёт)

1. Установите Allure CLI (один раз):
   - **Через npm** (кроссплатформенно, проще всего):
     ```bash
     npm install -g allure-commandline
     ```
   - **macOS (Homebrew):** `brew install allure`
   - **Windows (Scoop):** `scoop install allure`
   - Либо вручную скачать архив со страницы релизов: https://github.com/allure-framework/allure2/releases и добавить `bin/` в `PATH`.

2. Сгенерировать и открыть отчёт (поднимет локальный веб-сервер и откроет браузер автоматически):
   ```bash
   allure serve target/allure-results
   ```

### Вариант B — только через Maven (без установки Allure CLI)

```bash
mvn allure:report
```
Отчёт будет собран в `target/allure-report/index.html` — откройте этот файл в браузере.
(Статический HTML, часть интерактивных функций может работать ограниченно из-за политики браузера в отношении локальных файлов — поэтому вариант A предпочтительнее.)

---

## 5. Полный цикл одной командой

```bash
mvn clean test
allure serve target/allure-results
```

В отчёте вы увидите:
- Общее количество тестов, сколько прошло / упало
- Шаги каждого теста (через аннотации `@Step`)
- Скриншот автоматически прикрепляется к **упавшему** тесту (если такой будет)
- Виджет Environment — браузер, ОС, версия Java, режим запуска (headless/headed)

---

## 6. Запуск через GitHub Actions (CI) — без локальной настройки

В репозитории уже настроен workflow `.github/workflows/tests.yml`. Он запускается автоматически при пуше в ветку `main` (а также вручную, кнопкой "Run workflow" во вкладке Actions на GitHub) и делает следующее:

1. Поднимает Ubuntu-раннер с уже предустановленным Chrome.
2. Запускает `mvn clean test`.
3. Генерирует Allure-отчёт и публикует его как:
   - скачиваемый артефакт сборки (вкладка Actions → конкретный запуск → Artifacts),
   - страницу на GitHub Pages (ветка `gh-pages`), если в настройках репозитория включён Pages.

Это удобный способ получить отчёт, даже если на вашем компьютере не настроены Java/Maven/Chrome.

---

## 7. Частые проблемы

| Проблема | Решение |
|---|---|
| `mvn: command not found` | Maven не установлен или не добавлен в `PATH` — см. п. 1 |
| Браузер не открывается локально, тест зависает | Передайте `-Dheadless=false`, чтобы увидеть, что происходит в браузере |
| `SessionNotCreatedException: This version of ChromeDriver only supports Chrome version X` | Обновите Google Chrome до последней версии — WebDriverManager подбирает драйвер под установленный браузер автоматически |
| Тесты падают из‑за таймаутов | Проверьте интернет-соединение — тесты обращаются к живому сайту `uitestingplayground.com` |
| `allure: command not found` | Используйте Вариант B (`mvn allure:report`) или установите Allure CLI — см. п. 4 |

---

## 8. Почему тесты написаны именно так

Каждая страница сайта была изучена по исходному коду проекта
([github.com/Inflectra/ui-test-automation-playground](https://github.com/Inflectra/ui-test-automation-playground)),
чтобы локаторы и ожидания в тестах гарантированно соответствовали реальному поведению страниц:

- **Sample App** — `id` у полей ввода логина/пароля на странице **перегенерируется через JS** при загрузке, поэтому локаторы используют стабильный атрибут `name`, а не `id`.
- **Frames** — на странице нет статичного текста «PARENT/CHILD FRAME»: это **два вложенных iframe** с одинаковой кнопочной разметкой, предназначенные для отработки 4 разных стратегий поиска элемента (`data-*` атрибут, текст, `name`, XPath по классу). Тест прогоняет все четыре стратегии в наружном фрейме и одну — во вложенном.
- **Click** — на странице **нет перекрывающего элемента** (overlay). Вместо этого скрипт проверяет `event.screenX > 0`, отличая «настоящий» клик от программного. Поэтому в тесте используется обычный `WebElement.click()`, а не клик через JavaScript.
- **Dynamic ID** — `id` кнопки генерируется на сервере при каждой загрузке страницы; тест явно проверяет, что `id` меняется после `reload()`, и кликает по кнопке через локатор по классу.
- **Text Input** — текст кнопки обновляется **только в момент клика** (а не в реальном времени при наборе), и только если перед этим у поля ввода корректно сработали браузерные события `input`/`change` (что и обеспечивает `sendKeys()`). Тест явно проверяет оба состояния — до и после клика.

---
