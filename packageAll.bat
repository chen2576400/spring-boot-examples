@echo off
cd D:\work\IdeaProjects\PISX_TUNDRA
call mvn clean exec:exec
call mvn install
cd D:\work\IdeaProjects\Tundra-PMGT
call mvn clean install
cd D:\work\IdeaProjects\SINOTRUK_PMGT
call mvn clean install