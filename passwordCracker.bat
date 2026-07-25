@echo off
REM Wrapper de lancement de passwordCracker sous Windows.
REM   passwordCracker.bat -m DICO  -h 098f6bcd4621d373cade4e832627b4f6
REM   passwordCracker.bat -m BRUTE -h 098f6bcd4621d373cade4e832627b4f6

setlocal
set DIR=%~dp0
set OUT=%DIR%out

if not exist "%OUT%" (
    mkdir "%OUT%"
    dir /s /b "%DIR%src\*.java" > "%TEMP%\pc_sources.txt"
    javac -d "%OUT%" @"%TEMP%\pc_sources.txt"
    copy "%DIR%dictionary.txt" "%OUT%\" >nul 2>&1
)

cd /d "%DIR%"
java -cp "%OUT%" com.passwordcracker.PasswordCracker %*
endlocal
