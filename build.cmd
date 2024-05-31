@echo off
set bin=%~dp0bin
set src=%~dp0src

if exists %bin% rmdir /S /Q %bin% 
mkdir %bin% && javac -encoding utf8 -d %bin% -cp %src% %src%\Main.java

@REM jar cvfe %~dp0calculator.jar Main -C %bin% .