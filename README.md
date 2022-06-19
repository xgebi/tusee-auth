# Tusee Auth #

Tusee project is split into four repositories

1. [tusee-auth](https://github.com/xgebi/tusee-auth)
    - handles login, registration and other authentication things
2. tusee-app
    - handles every request apart from authentication
3. [tusee-fe](https://github.com/xgebi/tusee-fe)
   - front-end for back-ends
4. [tusee-prototype](https://github.com/xgebi/tusee-prototype)
   - Original prototype, currently useful as template for further development and contains migrations


## Build & Run ##

```sh
$ cd tusee-auth
$ sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.
