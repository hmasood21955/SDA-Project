@echo off
REM Clean previous class files (optional)
if exist bin rmdir /s /q bin
mkdir bin

REM Clear previous compile errors
del compile_errors.txt 2>nul
del sources.txt 2>nul

REM List all Java files in src and subfolders
dir /b /s src\*.java > sources.txt

REM Compile all at once
javac -d bin @sources.txt 2> compile_errors.txt

REM Check for compilation errors
findstr /r /c:"error:" compile_errors.txt >nul
if %errorlevel%==0 (
    echo Compilation failed. See compile_errors.txt for details.
    pause
    exit /b 1
)

REM Run the main class (no package)
cd bin
java ConsolidatedAttendanceSystem
if %errorlevel% neq 0 (
    REM Try with package (model)
    java model.ConsolidatedAttendanceSystem
    if %errorlevel% neq 0 (
        echo Could not find or run ConsolidatedAttendanceSystem. Please check the package and class name.
        pause
        exit /b 1
    )
)
pause 