cd "C:\Users\Janni\Downloads\minecraft\src\Map"
call mvn package
xcopy "C:\Users\Janni\Downloads\minecraft\src\Map\target\Map-0.0.1-SNAPSHOT.jar" "C:\Users\Janni\Downloads\minecraft\Devserver\1.6\plugins\Map-0.0.1-SNAPSHOT.jar" /y