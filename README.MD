Cara cetak testcase di kelas

1. untuk lihat report (ketik di terminal)
> allure serve demoapp/allure-results 

2. kalo error (ketik di terminal)
> scoop install allure

3. kalo scoop not recognized (ketik di terminal)
> Set-ExecutionPolicy RemoteSigned -Scope CurrentUser                             
> irm get.scoop.sh | iex


Kalau masih ga bisa pakai 3 cara diatas dan scoop sudah ada + allure sudah terinstall
> allure generate allure-results --clean -o allure-report
> allure open allure-report