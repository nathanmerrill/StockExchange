cd KoTHComm
git add .
git commit -m "Updates"
git push origin HEAD:master
cd ../
git checkout master
java -jar build/libs/StockExchange.jar   download compile
rm StockExchange.jar
git add .
git commit -m "Updates"
git push origin master
git checkout archives
git merge master
cp build/libs/StockExchange.jar StockExchange.jar
7z StockExchange.zip StockExchange.jar -aou
7z StockExchange.zip submissions -aou
7z StockExchangePlayers.zip submissions -aou
git add .
git commit -m "Updates"
git push origin archives