@echo off
cd E:\sinotruk\PISX_TUNDRA
call mvn clean exec:exec
call mvn install
cd E:\sinotruk\Tundra-PMGT
call mvn clean install
cd E:\sinotruk\SINOTRUK_PMGT
call mvn clean install